package com.pokeserver.poke.config;

import com.pokeserver.poke.service.PokemonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DataInitializer {

  @Bean
  public CommandLineRunner init(PokemonService pokemonService) { // Crawler 대신 Service 주입
    return args -> {
      System.out.println("🚀 PokeAPI 데이터 동기화 시작...");
      pokemonService.syncPokemonData(1, 151); // 1세대 동기화
      System.out.println("✅ 데이터 초기화 완료!");
    };
  }
}
