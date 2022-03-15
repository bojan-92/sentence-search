package com.project44.sentsearch;

import com.project44.sentsearch.controller.dto.SearchSentenceResponse;
import com.project44.sentsearch.controller.dto.SentenceDTO;
import com.project44.sentsearch.service.SentenceService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SentenceService.class})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SentenceServiceTests {

  @MockBean
  private SentenceService sentenceService;

  @Test
  void searchTestWord() {
    List<SentenceDTO> sentenceDTOS = new ArrayList<>();
    sentenceDTOS.add(SentenceDTO.builder().sentence("Test is important for testing test!").count(2).build());
    sentenceDTOS.add(SentenceDTO.builder().sentence("Test is important!").count(1).build());
    SearchSentenceResponse response = SearchSentenceResponse.builder().sentences(sentenceDTOS).word("test").build();

    Mockito.when(sentenceService.search("test")).thenReturn(response);
    Assertions.assertEquals(sentenceService.search("test").getWord(), "test");
  }

  @Test
  void searchTestOneSentences() {
    List<SentenceDTO> sentenceDTOS = new ArrayList<>();
    sentenceDTOS.add(SentenceDTO.builder().sentence("Test is important for testing test!").count(2).build());
    SearchSentenceResponse response = SearchSentenceResponse.builder().sentences(sentenceDTOS).word("test").build();

    Mockito.when(sentenceService.search("test")).thenReturn(response);
    Assertions.assertEquals(sentenceService.search("test").getSentences().size(), 1);
  }

  @Test
  void searchTestTwoSentences() {
    List<SentenceDTO> sentenceDTOS = new ArrayList<>();
    sentenceDTOS.add(SentenceDTO.builder().sentence("Test is important for testing test!").count(2).build());
    sentenceDTOS.add(SentenceDTO.builder().sentence("Test is important!").count(1).build());
    SearchSentenceResponse response = SearchSentenceResponse.builder().sentences(sentenceDTOS).word("test").build();

    Mockito.when(sentenceService.search("test")).thenReturn(response);
    Assertions.assertEquals(sentenceService.search("test").getSentences().size(), 2);
  }

  @Test
  void searchTestZeroSentences() {
    List<SentenceDTO> sentenceDTOS = new ArrayList<>();
    SearchSentenceResponse response = SearchSentenceResponse.builder().sentences(sentenceDTOS).word("test").build();

    Mockito.when(sentenceService.search("test")).thenReturn(response);
    Assertions.assertEquals(sentenceService.search("test").getSentences().size(), 0);
  }

}
