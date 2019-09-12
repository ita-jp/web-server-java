package com.example.web.server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FileManager {

    public Optional<BufferedReader> createBufferedReader(String path) {
        try {
            return Optional.of(Files.newBufferedReader(Paths.get(path)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<BufferedReader> createBufferedReaderFor404() {
        return createBufferedReader("./src/main/resources/404.html");
    }

}
