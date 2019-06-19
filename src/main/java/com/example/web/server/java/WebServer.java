package com.example.web.server.java;

import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@AllArgsConstructor
public class WebServer {

	private TimeManager timeManager;

	public OutputStream execute(ByteArrayInputStream input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write("HTTP/1.1 200 OK\n".getBytes());
		out.write("Date: Wed, 05 Jun 2019 04:02:51 GMT\n".getBytes());
		out.write("Server: MyServer/0.1\n".getBytes());
		out.write("Connection: Close\n".getBytes());
		out.write("Content-Type: text/html\n".getBytes()); // TODO introduce writer class?
		return out;
	}
}
