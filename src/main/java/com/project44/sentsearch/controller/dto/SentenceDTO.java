package com.project44.sentsearch.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentenceDTO {

  private String sentence;
  private int count;
}
