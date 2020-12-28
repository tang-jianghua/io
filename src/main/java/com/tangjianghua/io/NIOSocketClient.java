package com.tangjianghua.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class NIOSocketClient {

    public static void main(String[] args) {
        try {
            final SocketChannel open = SocketChannel.open();
            open.configureBlocking(false);
            open.connect(new InetSocketAddress("localhost",8081));
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            final String s = bufferedReader.readLine();
            final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            byteBuffer.put(s.getBytes());
            System.out.println(open.isConnected());
            open.write(byteBuffer);
            byteBuffer.clear();
           /* open.read(byteBuffer);
            byteBuffer.flip();
            final byte[] bytes = new byte[1024];
            byteBuffer.get(bytes);
            System.out.println(new String(bytes));
*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
