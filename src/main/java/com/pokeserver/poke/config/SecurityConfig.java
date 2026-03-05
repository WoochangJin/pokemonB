package com.pokeserver.poke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // API 통신을 위해 CSRF 비활성화
        .cors(cors -> cors.configurationSource(request -> {
          var config = new org.springframework.web.cors.CorsConfiguration();
          config.setAllowedOrigins(java.util.List.of("http://localhost:5173")); // 리액트 허용
          config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
          config.setAllowedHeaders(java.util.List.of("*"));
          return config;
        }))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/pokemon/**").permitAll() // 포켓몬 API는 누구나 접근 가능
            .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 접근 허용
            .anyRequest().authenticated()
        )
        .headers(headers -> headers.frameOptions(f -> f.disable())); // H2 콘솔 화면 깨짐 방지

    return http.build();
  }
}