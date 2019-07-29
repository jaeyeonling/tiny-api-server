package com.jaeyeonling.tiny.domain.engine.task;

import com.jaeyeonling.tiny.domain.engine.EngineSaveRequest;
import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.support.ControllerSupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.document;
import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.errorDocument;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EngineTaskControllerTests extends ControllerSupports {

    private static final PathParametersSnippet ENGINE_ID_PATH_PARAMETERS = pathParameters(
            parameterWithName("engineId").description("엔진 식별자"));

    public static final PathParametersSnippet ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS = pathParameters(
            parameterWithName("engineId").description("엔진 식별자"),
            parameterWithName("taskId").description("엔진 작업 식별자"));

    public static final RequestFieldsSnippet ENGINE_TASK_OPTION_REQUEST_FIELDS = requestFields(
            fieldWithPath("description").type(JsonFieldType.STRING).description("엔진 작업 설명"),
            fieldWithPath("complex").type(JsonFieldType.NUMBER).description("엔진 작업 복잡도\n" +
                    "현재는 해당 complex 값을 시간(seconds)을 계산하여 해당 시간만큼 지나야 작업이 끝난다."));

    public static final ResponseFieldsSnippet ENGINE_TASK_RESPONSE_FIELDS = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("엔진 작업 식별자"),
            fieldWithPath("createdDate").type(JsonFieldType.STRING).description("엔진 작업 생성 시간"),
            fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("엔진 작업 마지막 수정 시간"),
            fieldWithPath("description").type(JsonFieldType.STRING).description("엔진 작업 설명"),
            fieldWithPath("end").type(JsonFieldType.BOOLEAN).description("엔진 작업 완료 여부"));

    private static final ResponseFieldsSnippet ENGINE_TASK_ARRAY_RESPONSE_FIELDS = responseFields(
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("엔진 작업 식별자"),
            fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("엔진 작업 생성 시간"),
            fieldWithPath("[].lastModifiedDate").type(JsonFieldType.STRING).description("엔진 작업 마지막 수정 시간"),
            fieldWithPath("[].description").type(JsonFieldType.STRING).description("엔진 작업 설명"),
            fieldWithPath("[].end").type(JsonFieldType.BOOLEAN).description("엔진 작업 완료 여부"));

    private static final ResponseFieldsSnippet ENGINE_TASK_REPORT_RESPONSE_FIELDS = responseFields(
            fieldWithPath("engineName").type(JsonFieldType.STRING).description("엔진 이름"),
            fieldWithPath("description").type(JsonFieldType.STRING).description("엔진 작업 설명"),
            fieldWithPath("taskRunningTimeMillis").type(JsonFieldType.NUMBER).description("엔진 작업 소요 시간"));

    @DisplayName("엔진 작업을 조회한다.")
    @Test
    void findAll() throws Exception {
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineTaskControllerTestsfindAll")
                .build();

        final var result = post(engineSaveRequest, "/engines").andReturn();
        final var id = readTree(result).get("id").asText();

        for (int i = 0; i < 10; i++) {
            post(EngineTaskOption.builder()
                    .description("Description")
                    .complex(i)
                    .build(), "/engines/{id}/run", id);
        }

        // when / then
        get("/engines/{engineId}/tasks", id)
                .andExpect(status().isOk())
                .andDo(document("engine/task/find-all", ENGINE_ID_PATH_PARAMETERS,
                        ENGINE_TASK_ARRAY_RESPONSE_FIELDS));
    }

    @DisplayName("엔진 작업 정보를 조회한다.")
    @Test
    void findById() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineTaskControllerTestsfindById")
                .build();

        final var engineSaveResult = post(engineSaveRequest, "/engines").andReturn();
        final var engineId = readTree(engineSaveResult).get("id").asText();

        final var option = EngineTaskOption.builder()
                .description("Description")
                .build();

        final var engineRunResult = post(option, "/engines/{engineId}/run", engineId).andReturn();
        final var taskId = readTree(engineRunResult).get("id").asText();

        // when / then
        get("/engines/{engineId}/tasks/{taskId}", engineId, taskId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("lastModifiedDate").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("end").exists())
        .andDo(document("engine/task/find-by-id", ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS,
                ENGINE_TASK_RESPONSE_FIELDS));
    }

    @DisplayName("엔진 작업 상세 정보 조회 시 없는 아이디일 경우 에러를 발생한다.")
    @Test
    void findByIdThrow() throws Exception {
        get("/engines/{engineId}/tasks/{taskId}", "aaa", 999_999_999)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(ErrorStatus.ENGINE_TASK_NOT_FOUND.getCode()))
                .andDo(errorDocument("engine/task/not-found", ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS));
    }

    @DisplayName("엔진 작업이 끝나지 않았으면 예외처리한다.")
    @Test
    void reportThrowNotComplete() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineTaskControllerTestsreportThrowNotComplete")
                .build();

        final var engineSaveResult = post(engineSaveRequest, "/engines").andReturn();
        final var engineId = readTree(engineSaveResult).get("id").asText();

        final var option = EngineTaskOption.builder()
                .description("Description")
                .complex(100)
                .build();

        final var engineRunResult = post(option, String.format("/engines/%s/run", engineId)).andReturn();
        final var taskId = readTree(engineRunResult).get("id").asText();

        // when / then
        get("/engines/{engineId}/tasks/{taskId}/reports", engineId, taskId)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorStatus.ENGINE_TASK_NOT_COMPLETE.getCode()))
                .andDo(errorDocument("engine/task/report-not-complete", ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS));
    }

    @DisplayName("엔진 작업이 끝났으면 리포트를 만든다.")
    @Test
    void report() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineTaskControllerTestsreport")
                .build();

        final var engineSaveResult = post(engineSaveRequest, "/engines").andReturn();
        final var engineId = readTree(engineSaveResult).get("id").asText();

        final var option = EngineTaskOption.builder()
                .description("Description")
                .build();

        final var engineRunResult = post(option, String.format("/engines/%s/run", engineId)).andReturn();
        final var taskId = readTree(engineRunResult).get("id").asText();

        // when / then
        get("/engines/{engineId}/tasks/{taskId}/reports", engineId, taskId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("engineName").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("taskRunningTimeMillis").exists())
                .andDo(document("engine/task/report", ENGINE_ID_AND_TASK_ID_PATH_PARAMETERS,
                        ENGINE_TASK_REPORT_RESPONSE_FIELDS));
    }
}
