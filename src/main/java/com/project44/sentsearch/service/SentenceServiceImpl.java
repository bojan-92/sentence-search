package com.project44.sentsearch.service;

import com.project44.sentsearch.controller.dto.SearchSentenceResponse;
import com.project44.sentsearch.controller.dto.SentenceDTO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class SentenceServiceImpl implements SentenceService {

  @Autowired
  ResourceLoader resourceLoader;

  @Value("${sentences.file.name}")
  private String fileName;

  @Override
  public SearchSentenceResponse search(String word) {
    List<String> fileSentences = readFile();
    List<SentenceDTO> sentences = new ArrayList<>();

    for (String sentence : fileSentences) {
      String[] sentenceWords = sentence.split("\\s+");
      int count = countWordAppearance(word, sentenceWords);
      if (count > 0) {
        SentenceDTO sentenceDTO = SentenceDTO.builder().sentence(sentence).count(count).build();
        sentences.add(sentenceDTO);
      }
    }
    sentences.sort(Comparator.comparing(SentenceDTO::getCount).reversed());

    return SearchSentenceResponse.builder().sentences(sentences).word(word).build();
  }

  private List<String> readFile() {
    List<String> list = new ArrayList<>();
    Resource fileResource = resourceLoader.getResource("classpath:/files/" + fileName);
    try {
      list = Files.readAllLines(Paths.get(fileResource.getURI()),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

  private int countWordAppearance(String word, String[] sentenceWords) {
    int count = 0;
    for (String sentenceWord : sentenceWords) {
      if (isWordEqualsToSentenceWord(word, sentenceWord)) {
        count++;
      }
    }
    return count;
  }

  private boolean isWordEqualsToSentenceWord(String word, String sentenceWord) {
    return sentenceWord.equalsIgnoreCase(word) || sentenceWord.equalsIgnoreCase(word + ".") || sentenceWord
        .equalsIgnoreCase(word + ",") || sentenceWord.equalsIgnoreCase(word + "?") || sentenceWord
        .equalsIgnoreCase(word + "!");
  }

}
