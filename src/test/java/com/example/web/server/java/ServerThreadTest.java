package com.example.web.server.java;

import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerThreadTest {

    private static String DOCUMENT_ROOT = "src/test/resources";

    // try $ curl example.com --head
    @Test
    public void test() throws Exception {
        // ## Arrange ##
        val input = new ByteArrayInputStream("GET test.html HTTP/1.1\r\n".getBytes());
        val output = new ByteArrayOutputStream();

        val mockSocket = mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(input);
        when(mockSocket.getOutputStream()).thenReturn(output);

        val timeManager = mock(TimeManager.class);
        val time = "Wed, 05 Jun 2019 04:02:51 GMT";
        when(timeManager.nowAsRFC7231()).thenReturn(time);

        val mockReader = mock(BufferedReader.class);
        when(mockReader.readLine()).thenReturn("<b>test</b>").thenReturn(null);

        val fileManager = mock(FileManager.class);
        when(fileManager.createBufferedReader(any())).thenReturn(Optional.of(mockReader));

        val server = new ServerThread(timeManager, fileManager, DOCUMENT_ROOT, mockSocket);

        // ## Act ##
        server.run();

        // ## Assert
        val actual = output.toString();
        // @formatter:off
        assertThat(actual).isEqualTo(MultiLineStringBuilder.begin()
                .s("HTTP/1.1 200 OK").n()
                .s("Date: ").s(time).n()
                .s("Server: MyServer/0.1").n()
                .s("Connection: close").n()
                .s("Content-Type: text/html").n()
                .n()
                .s("<b>test</b>").n()
                .end());
        // @formatter:on
    }

    @Test
    public void test_404() throws Exception {
        // ## Arrange ##
        val input = new ByteArrayInputStream("GET test_bad_bad.html HTTP/1.1\r\n".getBytes());
        val output = new ByteArrayOutputStream();

        val mockSocket = mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(input);
        when(mockSocket.getOutputStream()).thenReturn(output);

        val timeManager = mock(TimeManager.class);
        val time = "Wed, 05 Jun 2019 04:02:51 GMT";
        when(timeManager.nowAsRFC7231()).thenReturn(time);

        val mockReader = mock(BufferedReader.class);
        when(mockReader.readLine()).thenReturn("<b>Not Found</b>").thenReturn(null);

        val fileManager = mock(FileManager.class);
        when(fileManager.createBufferedReader(any())).thenReturn(Optional.empty());
        when(fileManager.createBufferedReaderFor404()).thenReturn(Optional.of(mockReader));

        val server = new ServerThread(timeManager, fileManager, DOCUMENT_ROOT, mockSocket);

        // ## Act ##
        server.run();

        // ## Assert
        val actual = output.toString();
        // @formatter:off
        assertThat(actual).isEqualTo(MultiLineStringBuilder.begin()
                .s("HTTP/1.1 404 Not Found").n()
                .s("Date: ").s(time).n()
                .s("Server: MyServer/0.1").n()
                .s("Connection: close").n()
                .s("Content-Type: text/html").n()
                .n()
                .s("<b>Not Found</b>").n()
                .end());
        // @formatter:on
    }

    @NoArgsConstructor
    private static class MultiLineStringBuilder {

        private StringBuilder sb = new StringBuilder();

        public static MultiLineStringBuilder begin() {
            return new MultiLineStringBuilder();
        }

        public MultiLineStringBuilder s(String line) {
            sb.append(line);
            return this;
        }

        public MultiLineStringBuilder n() {
            sb.append("\r\n");
            return this;
        }

        public String end() {
            return sb.toString();
        }
    }
}
