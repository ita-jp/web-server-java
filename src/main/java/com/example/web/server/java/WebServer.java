package com.example.web.server.java;

import lombok.AllArgsConstructor;
import lombok.val;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
public class WebServer implements Runnable {

    private TimeManager timeManager;
    private String documentRoot;
    private Socket socket;

    private void writeLine(OutputStream out, String line) throws IOException {
        for (final char ch : line.toCharArray()) {
            out.write((int) ch);
        }
        out.write((int) '\r');
        out.write((int) '\n');
    }

    private void execute() throws IOException {
        val input = socket.getInputStream();
        val request = new HttpRequest(input);

        val output = socket.getOutputStream();
        writeLine(output, "HTTP/1.1 200 OK");
        writeLine(output, "Date: " + timeManager.nowAsRFC7231());
        writeLine(output, "Server: MyServer/0.1");
        writeLine(output, "Connection: Close");
        writeLine(output, "Content-Type: " + request.getContentType().getMediaType());
        writeLine(output, "");

        try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(documentRoot + "/" + request.getPath()))) {
            String line = null;
            while ((line = fileReader.readLine()) != null) {
                writeLine(output, line);
            }
        }
    }

    @Override
    public void run() {
        try (Closeable closeable = socket::close) {
            execute();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
