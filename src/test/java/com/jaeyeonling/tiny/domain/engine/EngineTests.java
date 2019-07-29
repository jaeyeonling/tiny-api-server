package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.domain.engine.command.MockEngineCommand;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EngineTests {

    @DisplayName("실행하여 EngineTask를 생성한다.")
    @Test
    void run() {
        // given
        final var engine = Engine.builder()
                .name("EngineTests1")
                .build();
        final var option = EngineTaskOption.builder()
                .description("description")
                .complex(100)
                .build();

        // when
        final var task = engine.run(new MockEngineCommand(), option);

        // then
        assertThat(task).isNotNull();
    }
}
