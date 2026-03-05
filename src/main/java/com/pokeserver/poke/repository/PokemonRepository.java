package com.pokeserver.poke.repository;

import com.pokeserver.poke.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
  Optional<Pokemon> findByKoName(String koName);

  boolean existsByKoName(String koName);
}