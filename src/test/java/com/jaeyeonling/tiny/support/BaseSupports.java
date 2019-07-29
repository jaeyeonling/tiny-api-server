package com.jaeyeonling.tiny.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
abstract class BaseSupports {

    @Autowired
    private ObjectMapper objectMapper;

    byte[] toBytes(final Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(value);
    }

    protected JsonNode readTree(final MvcResult mvcResult) throws IOException {
        return objectMapper.readTree(mvcResult.getResponse().getContentAsByteArray());
    }
}
