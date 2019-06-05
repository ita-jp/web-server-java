package com.example.web.server.java;

import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebServerTest {

	// try $ curl example.com --head
	@Test
	public void test() throws Exception {
		// ## Arrange ##
		val input = new ByteArrayInputStream("GET /index.html HTTP/1.1".getBytes());
		val server = new WebServer();

		// ## Act ##
		val output = server.execute(input);

		// ## Assert
		val actual = output.toString();
		assertThat(actual).isEqualTo(MultiLineStringBuilder.begin()
				.s("HTTP/1.1 200 OK")
				.s("Date: Wed, 05 Jun 2019 04:02:51 GMT")
				.end()
		);
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
