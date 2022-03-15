package com.project44.sentsearch.controller;

import com.project44.sentsearch.controller.dto.SearchSentenceResponse;
import com.project44.sentsearch.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentenceController {

  @Autowired
  private SentenceService sentenceService;

  @GetMapping("/count")
  public ResponseEntity<SearchSentenceResponse> search(@RequestParam String word){
    return new ResponseEntity<>(sentenceService.search(word), HttpStatus.OK);
  }
}
