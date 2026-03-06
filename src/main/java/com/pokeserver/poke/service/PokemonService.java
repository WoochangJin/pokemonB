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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PokemonService {

  private final PokemonRepository pokemonRepository;
  private final RestTemplate restTemplate = new RestTemplate();

  public List<PokemonNameDto> getAllPokemonNames() {
    return pokemonRepository.findAll().stream()
        .map(p -> PokemonNameDto.builder()
            .id(p.getId())
            .koName(p.getKoName())
            .enName(p.getEnName())
            .type1(p.getType1()) // DTO에 추가된 필드 매핑
            .type2(p.getType2()) // DTO에 추가된 필드 매핑
            .build())
        .toList();
  }

  @Transactional
  public void syncPokemonData(int startId, int endId) {
    for (int i = startId; i <= endId; i++) {
      try {
        // 1. 이름 정보 가져오기 (species 엔드포인트)
        String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + i;
        PokeApiNameDto nameResponse = restTemplate.getForObject(speciesUrl, PokeApiNameDto.class);

        // 2. 타입 정보 가져오기 (pokemon 엔드포인트)
        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + i;
        Map<String, Object> typeResponse = restTemplate.getForObject(pokemonUrl, Map.class);

        if (nameResponse != null && typeResponse != null) {
          // 한글/영어 이름 추출
          String koName = nameResponse.getNames().stream()
              .filter(n -> n.getLanguage().getName().equals("ko"))
              .map(PokeApiNameDto.NameEntry::getName)
              .findFirst().orElse("Unknown");

          String enName = nameResponse.getNames().stream()
              .filter(n -> n.getLanguage().getName().equals("en"))
              .map(PokeApiNameDto.NameEntry::getName)
              .findFirst().orElse("Unknown");

          // 타입 추출 (List<Map> 구조 파싱)
          List<Map<String, Object>> types = (List<Map<String, Object>>) typeResponse.get("types");
          String t1 = types.get(0).get("type") instanceof Map ?
              (String)((Map)types.get(0).get("type")).get("name") : "unknown";
          String t2 = types.size() > 1 ?
              (String)((Map)types.get(1).get("type")).get("name") : null;

          // 3. DB 저장
          Pokemon pokemon = Pokemon.builder()
              .id((long) i)
              .koName(koName)
              .enName(enName)
              .type1(t1)
              .type2(t2)
              .build();

          pokemonRepository.save(pokemon);
          System.out.println("Saved: #" + i + " " + koName + " [" + t1 + (t2 != null ? ", " + t2 : "") + "]");

          Thread.sleep(100);
        }
      } catch (Exception e) {
        System.err.println("Error at ID " + i + ": " + e.getMessage());
      }
    }
  }
}