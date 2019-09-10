package com.example.web.server.java;

import lombok.AllArgsConstructor;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
public class WebServer implements Runnable {

    private TimeManager timeManager;
    private String documentRoot;
    private Socket socket;

    private void execute() throws IOException {
        try (val input = socket.getInputStream(); val output = socket.getOutputStream();) {
            output.write("HTTP/1.1 200 OK\n".getBytes());
            output.write(("Date: " + timeManager.nowAsRFC7231() + "\n").getBytes());
            output.write("Server: MyServer/0.1\n".getBytes());
            output.write("Connection: Close\n".getBytes());
            output.write("Content-Type: text/html\n".getBytes()); // TODO introduce writer class?
            output.write("\n".getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String[] tokens = reader.readLine().split(" ");
            String path = tokens[1];

            try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(documentRoot + "/" + path))) {
                String line = null;
                while ((line = fileReader.readLine()) != null) {
                    output.write(line.getBytes());
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
