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
        val webServer = new WebServer(new TimeManager(), "./src/main/resources"); // TODO check document root directory
        try (val serverSocket = new ServerSocket(8080)) {
            val socket = serverSocket.accept();
            webServer.execute(socket.getInputStream(), socket.getOutputStream());
        }
    }

}
