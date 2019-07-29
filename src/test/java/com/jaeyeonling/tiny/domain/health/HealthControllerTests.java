package com.jaeyeonling.tiny.domain.health;

import com.jaeyeonling.tiny.docs.ApiDocumentUtils;
import com.jaeyeonling.tiny.support.ControllerSupports;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HealthControllerTests extends ControllerSupports {

    @DisplayName("상태를 확인한다.")
    @Test
    void health() throws Exception {
        get("/health")
                .andExpect(status().isOk())
                .andDo(ApiDocumentUtils.document("health-check"));
    }
}
