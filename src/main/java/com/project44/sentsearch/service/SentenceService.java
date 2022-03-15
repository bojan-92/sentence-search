package com.project44.sentsearch.service;

import com.project44.sentsearch.controller.dto.SearchSentenceResponse;

public interface SentenceService {

  SearchSentenceResponse search(String word);

}
