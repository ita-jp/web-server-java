package com.example.web.server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileManager {

    public Optional<BufferedReader> createBufferedReader(String documentRoot, String relativePath) {
        try {
            Path absolutePath = Paths.get(documentRoot, relativePath).toRealPath();
            Path documentRootAbsolutePath = Paths.get(documentRoot).toRealPath();

            if (Files.isDirectory(absolutePath)) {
                return Optional.empty();
            }

            if (!absolutePath.startsWith(documentRootAbsolutePath)) {
                return Optional.empty();
            }

            return Optional.of(Files.newBufferedReader(absolutePath));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<BufferedReader> createBufferedReaderFor404() {
        return createBufferedReader("./src/main/resources/", "404.html");
    }

}
