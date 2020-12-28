package com.tangjianghua.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author tangjianghua
 * @date 2020/7/3
 */
public class Test {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 10240);
        socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 10240);
        socketChannel.connect(new InetSocketAddress("localhost", 9090));
        socketChannel.configureBlocking(true);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(socketChannel.getOption(StandardSocketOptions.SO_RCVBUF));
        socketChannel.read(byteBuffer);
        System.out.println(byteBuffer.toString());
        byteBuffer.flip();
        byte[] bytes1 = new byte[8];
        byteBuffer.get(bytes1, 0, 8);
        //报文长度
        String length = new String(bytes1);

        //分配缓冲区
        int i = Integer.parseInt(length);
        ByteBuffer buffer = ByteBuffer.allocateDirect(i+8);
        buffer.put(bytes1);
        buffer.put(byteBuffer);

        while (buffer.position() < buffer.capacity()) {
            socketChannel.read(buffer);
            System.out.println(buffer.toString());
        }
        buffer.flip();
        byte[] bytes2 = new byte[buffer.limit()];
        buffer.get(bytes2);
    }
}
