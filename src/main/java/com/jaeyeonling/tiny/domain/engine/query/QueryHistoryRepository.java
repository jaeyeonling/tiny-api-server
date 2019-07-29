package com.jaeyeonling.tiny.domain.engine.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface QueryHistoryRepository extends JpaRepository<QueryHistory, Long> { }
