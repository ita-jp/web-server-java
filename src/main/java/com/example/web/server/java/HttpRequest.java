package com.example.web.server.java;

import lombok.Getter;
import lombok.val;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

    @Getter
    private String method;
    @Getter
    private String path;

    public HttpRequest(InputStream inputStream) throws IOException {
        int ch;
        val sb = new StringBuilder();
        while ((ch = inputStream.read()) != -1) {
            if (ch == '\r') {
                val requestLine = sb.toString().split(" +");
                method = requestLine[0];
                path = requestLine[1];
                break;
            }
            sb.append((char) ch);
        }
    }

}
