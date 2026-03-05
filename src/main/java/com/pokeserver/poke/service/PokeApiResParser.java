package com.pokeserver.poke.service;

import org.springframework.stereotype.Component;

import com.pokeserver.poke.domain.Pokemon;
import com.pokeserver.poke.dto.PokemonResponseDto;
@Component
public class PokeApiResParser{
  
  public Pokemon toPokeEntity(PokemonResponseDto dto){
    return Pokemon.builder()
      .enName(dto.getName())
      .build();
  }
}
