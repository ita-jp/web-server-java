package com.example.web.server.java;

import lombok.Data;

@Data
public class HttpRequest {

    private String method;
    private String path;
    private ContentType contentType;

}
