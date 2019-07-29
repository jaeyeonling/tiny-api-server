package com.jaeyeonling.tiny.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class ControllerSupports extends BaseSupports {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions get(final String urlTemplate,
                                final Object... uriVars) throws Exception {
        return request(RestDocumentationRequestBuilders.get(urlTemplate, uriVars));
    }

    protected ResultActions post(final Object content,
                                 final String urlTemplate,
                                 final Object... uriVars) throws Exception {
        return request(RestDocumentationRequestBuilders.post(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toBytes(content)));
    }

    private ResultActions request(final RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andDo(print());
    }
}
