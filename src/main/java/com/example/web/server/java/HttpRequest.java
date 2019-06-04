package com.example.web.server.java;

import lombok.Getter;
import lombok.val;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

public class HttpRequest {

    @Getter
    private String method;
    @Getter
    private String path;

    public HttpRequest(InputStream inputStream) throws IOException {
        val reader = new InputStreamReader(inputStream);
        val bufferedInputStream = new BufferedReader(reader);

        val iter = bufferedInputStream.lines().iterator();
        if (!iter.hasNext()) {
            return ;
        }
        val requestLine = iter.next().split(" +");
        method = requestLine[0];
        path = requestLine[1];
    }

}
