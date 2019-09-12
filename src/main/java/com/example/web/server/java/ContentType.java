package com.example.web.server.java;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContentType {
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private String mediaType;
}
