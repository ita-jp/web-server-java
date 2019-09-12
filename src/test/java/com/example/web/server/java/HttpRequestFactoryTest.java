package com.example.web.server.java;

import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestFactoryTest {

    @Test
    public void getMethod_GET() throws Exception {
        // ## Arrange ##
        val request = HttpRequestFactory.of(buildInputStream(
                "GET /index.html HTTP/1.1",
                ""
        ));

        // ## Act ##
        val method = request.getMethod();

        // ## Assert ##
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/index.html");
    }

    @Test
    public void getMethod_POST() throws Exception {
        // ## Arrange ##
        val request = HttpRequestFactory.of(buildInputStream(
                "POST /index.html HTTP/1.1",
                ""));

        // ## Act ##
        val method = request.getMethod();

        // ## Assert ##
        assertThat(method).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/index.html");
    }

    private InputStream buildInputStream(String... line) {
        return new ByteArrayInputStream(String.join("\r\n", line).getBytes());
    }
}
