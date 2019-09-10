package com.example.web.server.java;

import lombok.AllArgsConstructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
public class WebServer implements Runnable {

    private TimeManager timeManager;
    private String documentRoot;
    private InputStream input;
    private OutputStream out;

    private void execute() throws IOException {
        out.write("HTTP/1.1 200 OK\n".getBytes());
        out.write(("Date: " + timeManager.nowAsRFC7231() + "\n").getBytes());
        out.write("Server: MyServer/0.1\n".getBytes());
        out.write("Connection: Close\n".getBytes());
        out.write("Content-Type: text/html\n".getBytes()); // TODO introduce writer class?

        out.write("\n".getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String[] tokens = reader.readLine().split(" ");
        String path = tokens[1];

        try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(documentRoot + "/" + path))) {
            String line = null;
            while ((line = fileReader.readLine()) != null) {
                out.write(line.getBytes());
            }
        }
    }

    @Override
    public void run() {
        try {
            execute();
            input.close();
            out.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
