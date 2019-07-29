package com.jaeyeonling.tiny.domain.engine.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineTaskRepository extends JpaRepository<EngineTask, Long> { }
