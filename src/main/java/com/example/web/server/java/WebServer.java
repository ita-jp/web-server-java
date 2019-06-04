package com.example.web.server.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WebServer {

	public OutputStream execute(ByteArrayInputStream input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write("test".getBytes());
		return out;
	}
}
