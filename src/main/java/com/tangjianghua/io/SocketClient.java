package com.tangjianghua.io;

import java.io.*;
import java.net.Socket;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        m();
    }

    public static void m() {
        try (final Socket socket = new Socket("192.168.186.1", 9090)) {
//        try (final Socket socket = new Socket("192.168.186.66", 9090)) {
            socket.setTcpNoDelay(true);
            socket.setOOBInline(true);
            socket.setSoTimeout(0);
            socket.setSendBufferSize(1024);
            final OutputStream outputStream = socket.getOutputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                final String s = bufferedReader.readLine();
                if (s != null) {
                    outputStream.write(s.getBytes());
                 /*   final byte[] bytes = s.getBytes();
                    for (int i = 0; i < bytes.length; i++) {
                        outputStream.write(bytes[i]);
                    }*/
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
