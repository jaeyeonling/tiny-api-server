package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.domain.engine.EngineSaveRequest;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import com.jaeyeonling.tiny.support.ControllerSupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.document;
import static com.jaeyeonling.tiny.domain.engine.task.EngineTaskControllerTests.ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandHistoryControllerTests extends ControllerSupports {

    private static final ResponseFieldsSnippet ENGINE_TASK_COMMAND_HISTORY_ARRAY_RESPONSE_FIELDS = responseFields(
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("엔진 작업 식별자"),
            fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("엔진 작업 생성 시간"),
            fieldWithPath("[].engineName").type(JsonFieldType.STRING).description("엔진 이름"),
            fieldWithPath("[].engineDescription").type(JsonFieldType.STRING).description("엔진 설명"));

    @DisplayName("엔진 작업 실행 기록을 조회한다.")
    @Test
    void findAll() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("CommandHistoryControllerTestsfindAll")
                .build();

        final var engineSaveResult = post(engineSaveRequest, "/engines").andReturn();
        final var engineId = readTree(engineSaveResult).get("id").asText();

        final var option = EngineTaskOption.builder()
                .description("Description")
                .build();

        final var engineRunResult = post(option, String.format("/engines/%s/run", engineId)).andReturn();
        final var taskId = readTree(engineRunResult).get("id").asText();

        // when / then
        get("/engines/{engineId}/tasks/{taskId}/commands", engineId, taskId)
                .andExpect(status().isOk())
                .andDo(document("engine/task/command/find-all", ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS,
                        ENGINE_TASK_COMMAND_HISTORY_ARRAY_RESPONSE_FIELDS));
    }
}
