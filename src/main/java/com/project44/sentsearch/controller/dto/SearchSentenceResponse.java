package com.project44.sentsearch.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchSentenceResponse {

  private String word;
  List<SentenceDTO> sentences;
}
