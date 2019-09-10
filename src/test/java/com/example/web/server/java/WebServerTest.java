package com.example.web.server.java;

import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebServerTest {

	private static String DOCUMENT_ROOT = "src/test/resources";

	// try $ curl example.com --head
	@Test
	public void test() throws Exception {
		// ## Arrange ##
		val input = new ByteArrayInputStream("GET test.html HTTP/1.1".getBytes());
        val output = new ByteArrayOutputStream();
		val mockSocket = mock(Socket.class);
		when(mockSocket.getInputStream()).thenReturn(input);
		when(mockSocket.getOutputStream()).thenReturn(output);
		val timeManager = mock(TimeManager.class);
        val time = "Wed, 05 Jun 2019 04:02:51 GMT";
        when(timeManager.nowAsRFC7231()).thenReturn(time);
        val server = new WebServer(timeManager, DOCUMENT_ROOT, mockSocket);

        // ## Act ##
		server.run();

		// ## Assert
		val actual = output.toString();
		// @formatter:off
		assertThat(actual).isEqualTo(MultiLineStringBuilder.begin()
				.s("HTTP/1.1 200 OK").n()
				.s("Date: ").s(time).n()
				.s("Server: MyServer/0.1").n()
				.s("Connection: Close").n()
				.s("Content-Type: text/html").n()
				.n()
				.s("<b>It works!</b>")
				.end());
		// @formatter:on
	}

	@NoArgsConstructor
	private static class MultiLineStringBuilder {

		private StringBuilder sb = new StringBuilder();

		public static MultiLineStringBuilder begin() {
			return new MultiLineStringBuilder();
		}

		public MultiLineStringBuilder s(String line) {
			sb.append(line);
			return this;
		}

		public MultiLineStringBuilder n() {
			sb.append(System.lineSeparator());
			return this;
		}

		public String end() {
			return sb.toString();
		}
	}
}
