package com.jaeyeonling.tiny.domain.engine.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> { }
