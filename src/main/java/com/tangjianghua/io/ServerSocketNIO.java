package com.tangjianghua.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class ServerSocketNIO {

    //server socket listen property:
    private static final int RECEIVE_BUFFER = 1024;
    private static final int SO_TIMEOUT = 0;
    private static final boolean REUSE_ADDR = false;
    private static final int BACK_LOG = 2;
    //client socket listen property on server endpoint:
    private static final boolean CLI_KEEPALIVE = false;
    private static final boolean CLI_OOB = false;
    private static final int CLI_REC_BUF = 1024;
    private static final boolean CLI_REUSE_ADDR = false;
    private static final int CLI_SEND_BUF = 1024;
    private static final boolean CLI_LINGER = true;
    private static final int CLI_LINGER_N = 0;
    private static final int CLI_TIMEOUT = 0;
    private static final boolean CLI_NO_DELAY = true;


    public static void main(String[] args) {

        m();
    }
    public static void m(){
        try (final ServerSocketChannel open = ServerSocketChannel.open()) {
            open.bind(new InetSocketAddress(9090), BACK_LOG);
            open.configureBlocking(false);
//            open.setOption(StandardSocketOptions.TCP_NODELAY, false);
//            open.setOption(StandardSocketOptions.SO_KEEPALIVE, false);
//            open.setOption(StandardSocketOptions.SO_RCVBUF, RECEIVE_BUFFER);
            System.out.println("server listen 9090 start.");
            final LinkedList<SocketChannel> socketClients = new LinkedList<>();
            while (true) {
                final SocketChannel clientChannel = open.accept();
                if (clientChannel == null) {
//                    System.out.println("null.....");
                } else {
                    clientChannel.configureBlocking(false);
                    clientChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
                    //超时
                    clientChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                    clientChannel.setOption(StandardSocketOptions.SO_RCVBUF, CLI_REC_BUF);
                    clientChannel.setOption(StandardSocketOptions.SO_SNDBUF, CLI_SEND_BUF);
                    socketClients.add(clientChannel);
                    System.out.println("client " + clientChannel.getRemoteAddress() + ":" + clientChannel.socket().getPort());
                }
                for (int i = 0; i < socketClients.size(); i++) {
                    final SocketChannel socketChannel = socketClients.get(i);
                    final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(RECEIVE_BUFFER);
                    int read = 0;
                    while ((read = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        for (int j = 0; j < byteBuffer.limit(); j++) {
                            System.out.print(byteBuffer.getChar());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }}

}
