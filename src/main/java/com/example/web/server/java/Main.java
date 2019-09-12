package com.example.web.server.java;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.val;

import java.io.IOException;
import java.net.ServerSocket;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Main {

    public static void main(String[] args) throws IOException {
        try (val serverSocket = new ServerSocket(8080)) {
            while (true) {
                val socket = serverSocket.accept();
                val server = new ServerThread(new TimeManager(), "./src/main/resources", socket); // TODO check document root directory
                val thread = new Thread(server);
                thread.start();
            }
        }
    }

}
