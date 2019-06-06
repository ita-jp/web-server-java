package com.example.web.server.java;

import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebServerTest {

	// try $ curl example.com --head
	@Test
	public void test() throws Exception {
		// ## Arrange ##
		val input = new ByteArrayInputStream("GET /index.html HTTP/1.1".getBytes());
		val timeManager = mock(TimeManager.class);
		val time = "Wed, 05 Jun 2019 04:02:51 GMT";
		when(timeManager.nowAsRFC7231()).thenReturn(time);
		val server = new WebServer(timeManager);

		// ## Act ##
		val output = server.execute(input);

		// ## Assert
		val actual = output.toString();
		assertThat(actual).isEqualTo(MultiLineStringBuilder.begin().s("HTTP/1.1 200 OK").n().s("Date: ").s(time).end());
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
