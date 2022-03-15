package com.project44.sentsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.project44.sentsearch.controller.dto.SearchSentenceResponse;
import com.project44.sentsearch.controller.dto.SentenceDTO;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class SentenceControllerIntegrationTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Value("${sentences.file.name}")
  private String fileName;

  private List<String> sentences;

  @BeforeEach
  void setUp(){
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(Objects.requireNonNull(classLoader.getResource("files/" + fileName)).getFile());
    try {
      sentences = Files.readAllLines(Paths.get(file.getPath()),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void searchApiShouldReturnTwoSentence() {
    assertEquals(this.restTemplate
        .getForObject("http://localhost:" + port + "/count?word=eggplant", SearchSentenceResponse.class)
        .getSentences().size(), sentences.size());
  }

  @Test
  void searchApiShouldReturnValidWord() {
    assertEquals(this.restTemplate
        .getForObject("http://localhost:" + port + "/count?word=eggplant", SearchSentenceResponse.class)
        .getWord(), "eggplant");
  }

  @Test
  void searchApiShouldReturnStatus200() {
    ResponseEntity<SearchSentenceResponse> responseEntity = this.restTemplate
        .getForEntity("http://localhost:" + port + "/count?word=test", SearchSentenceResponse.class);
    assertEquals(200, responseEntity.getStatusCodeValue());
  }

  @Test
  void searchApiShouldCountWordAppearance() {
    ResponseEntity<SearchSentenceResponse> responseEntity = this.restTemplate
        .getForEntity("http://localhost:" + port + "/count?word=test", SearchSentenceResponse.class);

    for (SentenceDTO s : Objects.requireNonNull(responseEntity.getBody()).getSentences()) {
      if (s.getSentence().contains("test")) {
        assertNotEquals(s.getCount(), 0);
      }
    }
  }

}
