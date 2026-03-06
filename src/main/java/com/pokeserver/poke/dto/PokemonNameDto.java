package com.pokeserver.poke.dto;

import aQute.bnd.annotation.metatype.Meta;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PokemonNameDto {
  private long id;
  private String koName;
  private String enName;
  private String type1;
  private String type2;

}
