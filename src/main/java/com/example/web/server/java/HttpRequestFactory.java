package com.example.web.server.java;

import lombok.val;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {

    public static HttpRequest of(InputStream inputStream) throws IOException {
        val request = new HttpRequest();
        int ch;
        val sb = new StringBuilder();
        while ((ch = inputStream.read()) != -1) {
            if (ch == '\r') {
                val requestLine = sb.toString().split(" +");
                request.setMethod(requestLine[0]);
                request.setPath(requestLine[1]);
                break;
            }
            sb.append((char) ch);
        }

        request.setContentType(toContentType(request.getPath()));

        return request;
    }

    public static ContentType toContentType(String path) {
        String[] tokens = path.split("\\.");
        String suffix = tokens[tokens.length - 1];
        return FileSuffixToContentType.lookup(suffix);
    }

    private static class FileSuffixToContentType {
        private static final Map<String, ContentType> suffix2ContentType;

        static {
            val map = new HashMap<String, ContentType>();
            map.put("html", ContentType.TEXT_HTML);
            map.put("css", ContentType.TEXT_CSS);
            suffix2ContentType = Collections.unmodifiableMap(map);
        }

        public static ContentType lookup(String suffix) {
            return suffix2ContentType.getOrDefault(suffix, ContentType.APPLICATION_OCTET_STREAM);
        }
    }
}
