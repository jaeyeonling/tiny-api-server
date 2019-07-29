package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.support.ControllerSupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;

import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.document;
import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.errorDocument;
import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.fieldErrorDocument;
import static com.jaeyeonling.tiny.domain.engine.task.EngineTaskControllerTests.ENGINE_TASK_OPTION_REQUEST_FIELDS;
import static com.jaeyeonling.tiny.domain.engine.task.EngineTaskControllerTests.ENGINE_TASK_RESPONSE_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EngineControllerTests extends ControllerSupports {

    private static final RequestFieldsSnippet ENGINE_REQUEST_FIELDS = requestFields(
            fieldWithPath("name").type(JsonFieldType.STRING).description("엔진 이름"));

    private static final ResponseFieldsSnippet ENGINE_RESPONSE_FIELDS = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("엔진 식별자"),
            fieldWithPath("createdDate").type(JsonFieldType.STRING).description("엔진 생성 시간"),
            fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("엔진 마지막 수정 시간"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("엔진 이름"));

    private static final ResponseFieldsSnippet ENGINE_RESPONSE_ARRAY_FIELDS = responseFields(
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("엔진 식별자"),
            fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("엔진 생성 시간"),
            fieldWithPath("[].lastModifiedDate").type(JsonFieldType.STRING).description("엔진 마지막 수정 시간"),
            fieldWithPath("[].name").type(JsonFieldType.STRING).description("엔진 이름"));

    private static final PathParametersSnippet ID_PATH_PARAMETERS = pathParameters(
            parameterWithName("id").description("엔진 식별자"));

    @DisplayName("엔진을 등록하고 리턴값을 검증한다.")
    @Test
    void save() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineControllerTests1")
                .build();

        // when / then
        post(engineSaveRequest, "/engines")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(engineSaveRequest.getName()))
                .andDo(document("engine/save", ENGINE_REQUEST_FIELDS, ENGINE_RESPONSE_FIELDS));
    }

    @DisplayName("엔진 등록 시 name의 값이 적으면 예외처리 한다.")
    @Test
    void shorterThanMinName() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("N")
                .build();

        // when / then
        post(engineSaveRequest, "/engines")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorStatus.METHOD_ARGUMENT_NOT_VALID.getCode()))
                .andExpect(jsonPath("message").value(ErrorStatus.METHOD_ARGUMENT_NOT_VALID.name()))
                .andDo(fieldErrorDocument("engine/save-shorter-than-min-name", ENGINE_REQUEST_FIELDS));
    }

    @DisplayName("엔진 등록 시 name의 값이 크면 예외처리 한다.")
    @Test
    void longerThanMaxName() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("N".repeat(101))
                .build();

        // when / then
        post(engineSaveRequest, "/engines")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorStatus.METHOD_ARGUMENT_NOT_VALID.getCode()))
                .andExpect(jsonPath("message").value(ErrorStatus.METHOD_ARGUMENT_NOT_VALID.name()))
                .andDo(fieldErrorDocument("engine/save-longer-than-max-name", ENGINE_REQUEST_FIELDS));
    }

    @DisplayName("엔진 리스트를 조회한다.")
    @Test
    void findAll() throws Exception {
        for (int i = 0; i < 10; i++) {
            post(EngineSaveRequest.builder()
                    .name("EngineControllerTestsfindAll" + i)
                    .build(), "/engines");
        }

        // when / then
        get("/engines")
                .andExpect(status().isOk())
                .andDo(document("engine/find-all", ENGINE_RESPONSE_ARRAY_FIELDS));
    }

    @DisplayName("엔진 상세 정보를 조회한다.")
    @Test
    void findById() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineControllerTests2")
                .build();

        final var result = post(engineSaveRequest, "/engines").andReturn();
        final var id = readTree(result).get("id").asText();

        // when / then
        get("/engines/{id}", id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(engineSaveRequest.getName()))
                .andDo(document("engine/find-by-id", ID_PATH_PARAMETERS, ENGINE_RESPONSE_FIELDS));
    }

    @DisplayName("엔진 상세 정보 조회 시 없는 아이디일 경우 에러를 발생한다.")
    @Test
    void findByIdThrow() throws Exception {
        get("/engines/{id}", 999_999_999)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(ErrorStatus.ENGINE_NOT_FOUND.getCode()))
                .andDo(errorDocument("engine/not-found", ID_PATH_PARAMETERS));
    }

    @DisplayName("엔진을 실행하여 작업을 만든다.")
    @Test
    void run() throws Exception {
        // given
        final var engineSaveRequest = EngineSaveRequest.builder()
                .name("EngineControllerTests3")
                .build();

        final var result = post(engineSaveRequest, "/engines").andReturn();
        final var id = readTree(result).get("id").asText();

        final var option = EngineTaskOption.builder()
                .description("Description")
                .build();

        // when / then
        post(option, "/engines/{id}/run", id)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("lastModifiedDate").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("end").exists())
                .andDo(document("engine/run", ID_PATH_PARAMETERS, ENGINE_TASK_OPTION_REQUEST_FIELDS,
                        ENGINE_TASK_RESPONSE_FIELDS));
    }
}
