package com.pokeserver.poke.dto;

import aQute.bnd.annotation.metatype.Meta;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PokemonNameDto {
  private long id;
  private String koName;
  private String enName;


}
