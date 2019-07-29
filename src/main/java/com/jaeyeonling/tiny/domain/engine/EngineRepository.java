package com.jaeyeonling.tiny.domain.engine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {

    Optional<Engine> findByName(final String name);
}
