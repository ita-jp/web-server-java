package com.example.web.server.java;

import lombok.AllArgsConstructor;
import lombok.val;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

@AllArgsConstructor
public class ServerThread implements Runnable {

    private TimeManager timeManager;
    private FileManager fileManager;
    private String documentRoot;
    private Socket connection;

    private void writeLine(OutputStream out, String line) throws IOException {
        for (final char ch : line.toCharArray()) {
            out.write((int) ch);
        }
        out.write((int) '\r');
        out.write((int) '\n');
    }

    private void execute() throws IOException {
        val request = HttpRequestFactory.of(connection.getInputStream());

        val output = connection.getOutputStream();
        Optional<BufferedReader> optBufferedReader = fileManager.createBufferedReader(documentRoot, request.getPath());

        if (!optBufferedReader.isPresent()) {
            buildFileNotFoundResponse(output);
            return;
        }

        buildOKResponse(request, output, optBufferedReader.get());
    }

    private void writeLine(OutputStream output, BufferedReader reader) throws IOException {
        try (Closeable fileReader = reader) {
            String line;
            while ((line = reader.readLine()) != null) {
                writeLine(output, line);
            }
        }
    }

    private void buildOKResponse(HttpRequest request, OutputStream output, BufferedReader bufferedReader) throws IOException {
        writeLine(output, "HTTP/1.1 200 OK");
        writeLine(output, "Date: " + timeManager.nowAsRFC7231());
        writeLine(output, "Server: MyServer/0.1");
        writeLine(output, "Connection: close");
        writeLine(output, "Content-Type: " + request.getContentType().getMediaType());
        writeLine(output, "");
        writeLine(output, bufferedReader);
    }

    private void buildFileNotFoundResponse(OutputStream output) throws IOException {
        writeLine(output, "HTTP/1.1 404 Not Found");
        writeLine(output, "Date: " + timeManager.nowAsRFC7231());
        writeLine(output, "Server: MyServer/0.1");
        writeLine(output, "Connection: close");
        writeLine(output, "Content-Type: " + ContentType.TEXT_HTML.getMediaType());
        writeLine(output, "");
        writeLine(output, fileManager.createBufferedReaderFor404().get());
    }

    @Override
    public void run() {
        try (Closeable closeable = connection) {
            execute();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
