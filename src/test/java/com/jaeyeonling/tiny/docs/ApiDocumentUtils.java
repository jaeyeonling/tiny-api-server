package com.jaeyeonling.tiny.docs;

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public final class ApiDocumentUtils {

    private static final ResponseFieldsSnippet FIELD_ERROR_RESPONSE_FIELDS = responseFields(
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("에러 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"),
            fieldWithPath("fieldErrors").type(JsonFieldType.ARRAY).description("필드 에러 발생 시 에러 세부 내용"),
            fieldWithPath("fieldErrors[*].field").type(JsonFieldType.STRING).description("필드 에러 발생 필드 이름"),
            fieldWithPath("fieldErrors[*].value").type(JsonFieldType.STRING).description("필드 에러 발생 필드 값"),
            fieldWithPath("fieldErrors[*].reason").type(JsonFieldType.STRING).description("필드 에러 발생 이유"));

    private static final ResponseFieldsSnippet ERROR_RESPONSE_FIELDS = responseFields(
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("에러 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"),
            fieldWithPath("fieldErrors").type(JsonFieldType.ARRAY).description("필드 에러 발생 시 에러 세부 내용"));

    private ApiDocumentUtils() { }

    public static RestDocumentationResultHandler document(final String identifier,
                                                          final PathParametersSnippet pathParameters,
                                                          final RequestFieldsSnippet requestFields,
                                                          final ResponseFieldsSnippet responseFields) {
        return MockMvcRestDocumentation.document(identifier, getDocumentRequest(), getDocumentResponse(),
                pathParameters, requestFields, responseFields);
    }

    public static RestDocumentationResultHandler document(final String identifier,
                                                          final RequestFieldsSnippet requestFields,
                                                          final ResponseFieldsSnippet responseFields) {
        return MockMvcRestDocumentation.document(identifier, getDocumentRequest(), getDocumentResponse(),
                pathParameters(), requestFields, responseFields);
    }

    public static RestDocumentationResultHandler document(final String identifier,
                                                          final ResponseFieldsSnippet responseFields) {
        return MockMvcRestDocumentation.document(identifier, getDocumentRequest(), getDocumentResponse(),
                pathParameters(), responseFields);
    }

    public static RestDocumentationResultHandler document(final String identifier,
                                                          final PathParametersSnippet pathParameters,
                                                          final ResponseFieldsSnippet responseFields) {
        return MockMvcRestDocumentation.document(identifier, getDocumentRequest(), getDocumentResponse(),
                pathParameters, responseFields);
    }

    public static RestDocumentationResultHandler document(final String identifier) {
        return MockMvcRestDocumentation.document(identifier, getDocumentRequest(), getDocumentResponse(),
                pathParameters());
    }

    public static RestDocumentationResultHandler errorDocument(final String identifier) {
        return document(identifier, ERROR_RESPONSE_FIELDS);
    }

    public static RestDocumentationResultHandler errorDocument(final String identifier,
                                                               final PathParametersSnippet pathParameters) {
        return document(identifier, pathParameters, ERROR_RESPONSE_FIELDS);
    }

    public static RestDocumentationResultHandler errorDocument(final String identifier,
                                                               final RequestFieldsSnippet requestFields) {
        return document(identifier, requestFields, ERROR_RESPONSE_FIELDS);
    }

    public static RestDocumentationResultHandler fieldErrorDocument(final String identifier) {
        return document(identifier, FIELD_ERROR_RESPONSE_FIELDS);
    }

    public static RestDocumentationResultHandler fieldErrorDocument(final String identifier,
                                                               final PathParametersSnippet pathParameters) {
        return document(identifier, pathParameters, FIELD_ERROR_RESPONSE_FIELDS);
    }

    public static RestDocumentationResultHandler fieldErrorDocument(final String identifier,
                                                               final RequestFieldsSnippet requestFields) {
        return document(identifier, requestFields, FIELD_ERROR_RESPONSE_FIELDS);
    }

    private static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(prettyPrint());
    }

    private static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
