package com.project44.sentsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.project44.sentsearch.controller.dto.SearchSentenceResponse;
import com.project44.sentsearch.controller.dto.SentenceDTO;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Test
  void searchApiShouldReturnOneSentence() {
    assertEquals(this.restTemplate
        .getForObject("http://localhost:" + port + "/count?word=eggplant", SearchSentenceResponse.class)
        .getSentences().size(), 1);
  }

  @Test
  void searchApiShouldReturnZeroSentences() {
    assertEquals(this.restTemplate
        .getForObject("http://localhost:" + port + "/count?word=egg", SearchSentenceResponse.class)
        .getSentences().size(), 0);
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

  @Test
  void searchApiShouldReturnDecreasingCountOrder() {
    ResponseEntity<SearchSentenceResponse> responseEntity = this.restTemplate
        .getForEntity("http://localhost:" + port + "/count?word=test", SearchSentenceResponse.class);

    assertEquals(Objects.requireNonNull(responseEntity.getBody()).getSentences().get(0).getCount(), 2);
  }

}
