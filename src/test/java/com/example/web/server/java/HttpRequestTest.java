package com.example.web.server.java;

import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

	@Test
	public void getMethod_GET() throws Exception {
		// ## Arrange ##
		val request = new HttpRequest(new ByteArrayInputStream("GET /index.html HTTP/1.1".getBytes()));

		// ## Act ##
		val method = request.getMethod();

		// ## Assert ##
		assertThat(request.getMethod()).isEqualTo("GET");
		assertThat(request.getPath()).isEqualTo("/index.html");
	}

	@Test
	public void getMethod_POST() throws Exception {
		// ## Arrange ##
		val request = new HttpRequest(new ByteArrayInputStream("POST /index.html HTTP/1.1".getBytes()));

		// ## Act ##
		val method = request.getMethod();

		// ## Assert ##
		assertThat(method).isEqualTo("POST");
		assertThat(request.getPath()).isEqualTo("/index.html");
	}

}
