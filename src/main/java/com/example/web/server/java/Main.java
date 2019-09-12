package com.example.web.server.java;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.val;

import java.io.IOException;
import java.net.ServerSocket;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Main {

    public static void main(String[] args) throws IOException {
        val timeManager = new TimeManager();
        val fileManager = new FileManager();

        try (val server = new ServerSocket(8080)) {
            while (true) {
                val connection = server.accept();
                val serverThread = new ServerThread(timeManager, fileManager, "./src/main/resources", connection); // TODO check document root directory
                val thread = new Thread(serverThread);
                thread.start();
            }
        }
    }

}
