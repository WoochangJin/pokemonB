package com.pokeserver.poke.service;

import com.pokeserver.poke.domain.Pokemon;
import com.pokeserver.poke.dto.PokeApiNameDto;
import com.pokeserver.poke.dto.PokemonNameDto;
import com.pokeserver.poke.repository.PokemonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonService {

// PokemonService.java 내부

  public List<PokemonNameDto> getAllPokemonNames() {
    return pokemonRepository.findAll().stream()
        .map(p -> PokemonNameDto.builder()
            .id(p.getId())
            .koName(p.getKoName())
            .enName(p.getEnName())
            .build())
        .toList(); // Java 16+ 기준, 이전 버전은 .collect(Collectors.toList())
  }

  private final PokemonRepository pokemonRepository;
  private final RestTemplate restTemplate = new RestTemplate();

  @Transactional
  public void syncPokemonData(int startId, int endId) {
    for (int i = startId; i <= endId; i++) {
      String url = "https://pokeapi.co/api/v2/pokemon-species/" + i;

      try {
        // 1. API 호출
        PokeApiNameDto response = restTemplate.getForObject(url, PokeApiNameDto.class);

        if (response != null && response.getNames() != null) {
          // 2. 한글 이름과 영어 이름 추출
          String koName = response.getNames().stream()
              .filter(n -> n.getLanguage().getName().equals("ko"))
              .map(PokeApiNameDto.NameEntry::getName)
              .findFirst().orElse("Unknown");

          String enName = response.getNames().stream()
              .filter(n -> n.getLanguage().getName().equals("en"))
              .map(PokeApiNameDto.NameEntry::getName)
              .findFirst().orElse("Unknown");

          // 3. DB 저장 (ID는 도감번호 i)
          Pokemon pokemon = Pokemon.builder()
              .id((long) i)
              .koName(koName)
              .enName(enName)
              .build();

          pokemonRepository.save(pokemon);
          System.out.println("Saved: #" + i + " " + koName + " (" + enName + ")");

          // API 과부하 방지를 위한 미세한 딜레이
          Thread.sleep(100);
        }
      } catch (Exception e) {
        System.err.println("Error at ID " + i + ": " + e.getMessage());
      }
    }
  }
}