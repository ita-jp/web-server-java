package com.example.web.server.java;

import lombok.val;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebServerTest {

	@Test
	public void test() throws Exception {
		// ## Arrange ##
		val input = new ByteArrayInputStream("hoge".getBytes());
		val server = new WebServer();

		// ## Act ##
		val output = server.execute(input);

		// ## Assert
		val actual = output.toString();
		assertThat(actual).isEqualTo("test");
	}
}
