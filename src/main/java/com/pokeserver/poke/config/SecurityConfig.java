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
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(request -> {
          var config = new org.springframework.web.cors.CorsConfiguration();
          // [수정] 프론트엔드 배포 주소와 로컬 주소를 모두 허용합니다.
          config.setAllowedOrigins(java.util.List.of(
              "http://localhost:5173",
              "https://your-frontend-url.onrender.com" // 배포 후 생길 프론트 주소 입력
          ));
          config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
          config.setAllowedHeaders(java.util.List.of("*"));
          return config;
        }))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/pokemon/**").permitAll()
            // H2 콘솔은 실제 배포 환경(PostgreSQL 사용)에서는 필요 없으므로 제거해도 무방합니다.
            .anyRequest().authenticated()
        );

    return http.build();
  }
}