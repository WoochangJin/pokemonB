package com.pokeserver.poke.controller;

import com.pokeserver.poke.domain.Pokemon;
import com.pokeserver.poke.dto.PokemonNameDto;
import com.pokeserver.poke.repository.PokemonRepository;
import com.pokeserver.poke.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
@RequiredArgsConstructor
// 리액트(보통 3000포트)에서 오는 요청을 허용합니다.
@CrossOrigin(origins = "http://localhost:5173")
public class PokemonController {

  private final PokemonService pokemonService;

  @GetMapping("/names")
  public List<PokemonNameDto> getPokemonNames() {
    // DB에 저장된 모든 포켓몬(id, koName, enName) 목록을 반환합니다.
    return pokemonService.getAllPokemonNames();
  }

  @GetMapping("/pokeInfo")
  public
}