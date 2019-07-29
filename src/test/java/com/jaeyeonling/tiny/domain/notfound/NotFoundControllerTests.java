package com.jaeyeonling.tiny.domain.notfound;

import com.jaeyeonling.tiny.support.ControllerSupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.jaeyeonling.tiny.docs.ApiDocumentUtils.errorDocument;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotFoundControllerTests extends ControllerSupports {

    @DisplayName("페이지를 찾을 수 없다.")
    @Test
    void notFound() throws Exception {
        get("/asdasdada")
                .andExpect(status().isNotFound())
                .andDo(errorDocument("not-found"));
    }
}
