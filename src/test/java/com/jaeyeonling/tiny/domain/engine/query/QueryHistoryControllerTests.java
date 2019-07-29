package com.jaeyeonling.tiny.domain.engine.query;

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

class QueryHistoryControllerTests extends ControllerSupports {

    private static final ResponseFieldsSnippet ENGINE_TASK_QUERY_HISTORY_ARRAY_RESPONSE_FIELDS = responseFields(
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("엔진 작업 식별자"),
            fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("엔진 작업 생성 시간"),
            fieldWithPath("[].engineName").type(JsonFieldType.STRING).description("엔진 이름"),
            fieldWithPath("[].engineDescription").type(JsonFieldType.STRING).description("엔진 설명"));

    @DisplayName("엔진 작업 리포팅 기록 리스트를 조회한다.")
    @Test
    void findAll() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("QueryHistoryControllerTestsfindAll")
                .build();

        final var engineSaveResult = post(engineSaveRequest, "/engines").andReturn();
        final var engineId = readTree(engineSaveResult).get("id").asText();

        final var option = EngineTaskOption.builder()
                .description("Description")
                .build();

        final var engineRunResult = post(option, String.format("/engines/%s/run", engineId)).andReturn();
        final var taskId = readTree(engineRunResult).get("id").asText();

        for (int i = 0; i < 10; i++) {
            get("/engines/{engineId}/tasks/{taskId}/reports", engineId, taskId);
        }

        // when / then
        get("/engines/{engineId}/tasks/{taskId}/queries", engineId, taskId)
                .andExpect(status().isOk())
                .andDo(document("engine/task/query/find-all", ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS,
                        ENGINE_TASK_QUERY_HISTORY_ARRAY_RESPONSE_FIELDS));
    }
}
