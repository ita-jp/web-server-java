package com.example.web.server.java;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.val;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Main {

    public static void main(String[] args) throws IOException {
        try (val serverSocket = new ServerSocket(8080)) {
            while (true) {
                val socket = serverSocket.accept();
                val in = socket.getInputStream();
                val out = socket.getOutputStream();
                val server = new WebServer(new TimeManager(), "./src/main/resources", in, out); // TODO check document root directory
                new Thread(server).start();
            }
        }
    }

}
