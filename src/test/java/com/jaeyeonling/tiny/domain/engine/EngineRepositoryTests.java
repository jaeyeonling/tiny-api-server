package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.support.RepositorySupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class EngineRepositoryTests extends RepositorySupports {

    @Autowired
    private EngineRepository engineRepository;

    @DisplayName("Engine을 등록한다.")
    @Test
    void save() {
        // given
        final var engine = Engine.builder()
                .name("EngineRepositoryTests1")
                .build();

        // when
        final var saveEngine = engineRepository.save(engine);

        // then
        assertThat(saveEngine.getName()).isEqualTo(engine.getName());
    }

    @DisplayName("Engine을 등록하고 이름으로 조회한다.")
    @Test
    void saveAndFindByName() {
        // given
        final var name = "EngineRepositoryTests2";
        final var engine = Engine.builder()
                .name(name)
                .build();

        engineRepository.save(engine);

        // when
        final var findEngine = engineRepository.findByName(name)
                .orElseThrow();

        // then
        assertThat(findEngine.getName()).isEqualTo(engine.getName());
    }

    @DisplayName("Engine의 이름이 중복되면 예외처리 한다.")
    @Test
    void throwDataIntegrityViolationException() {
        // given
        final var name = "EngineRepositoryTests3";
        engineRepository.save(Engine.builder()
                .name(name)
                .build());

        // when / then
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> engineRepository.save(Engine.builder()
                        .name(name)
                        .build()));
    }
}



