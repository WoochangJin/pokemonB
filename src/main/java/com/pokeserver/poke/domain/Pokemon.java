package com.pokeserver.poke.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  Pokemon {
  @Id
  @Column(name = "id")
  private Long id;

  private String koName;

  private String enName;
}
