package com.pokeserver.poke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PokeApiNameDto {
  // API 응답의 "names" 필드와 매핑
  private List<NameEntry> names;

  @Data
  public static class NameEntry {
    private String name;
    private Language language;
  }

  @Data
  public static class Language {
    private String name; // "ko", "en" 등
  }
}