package com.jaeyeonling.tiny.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (final PlatformException e) {
            recovery(response, e);

        } catch (final Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("UncaughtException", e);
            }

            recovery(response, UncaughtException.getInstance());
        }
    }

    private void recovery(final HttpServletResponse response,
                          final PlatformException platformException) throws IOException {
        response.setStatus(platformException.getRawHttpStatus());
        response.getWriter().write(writeResponse(platformException.toBody()));
    }

    private String writeResponse(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
