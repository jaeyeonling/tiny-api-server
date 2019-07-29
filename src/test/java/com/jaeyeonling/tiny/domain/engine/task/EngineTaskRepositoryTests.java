package com.jaeyeonling.tiny.domain.engine.task;

import com.jaeyeonling.tiny.domain.engine.Engine;
import com.jaeyeonling.tiny.domain.engine.EngineRepository;
import com.jaeyeonling.tiny.domain.engine.command.MockEngineCommand;
import com.jaeyeonling.tiny.support.RepositorySupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EngineTaskRepositoryTests extends RepositorySupports {

    @Autowired
    private EngineRepository engineRepository;

    @Autowired
    private EngineTaskRepository engineTaskRepository;

    @DisplayName("EngineTask를 등록한다.")
    @Test
    void save() {
        // given
        final var engine = Engine.builder()
                .name("EngineTaskRepositoryTests1")
                .build();
        final var option = EngineTaskOption.builder()
                .description("description")
                .complex(100)
                .build();
        final var task = engine.run(new MockEngineCommand(), option);

        engineRepository.save(engine);

        // when
        final var saveTask = engineTaskRepository.save(task);

        // then
        assertThat(saveTask.getEngineName()).isEqualTo(engine.getName());
        assertThat(saveTask.getDescription()).isEqualTo(option.getDescription());
        assertThat(saveTask.isEnd()).isFalse();
    }
}
