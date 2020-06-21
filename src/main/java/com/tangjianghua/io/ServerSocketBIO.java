package com.tangjianghua.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class ServerSocketBIO {

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
        try (final ServerSocket serverSocket = new ServerSocket(9090, BACK_LOG)) {
            //接受缓存
            serverSocket.setReceiveBufferSize(RECEIVE_BUFFER);
            //地址复用
            serverSocket.setReuseAddress(REUSE_ADDR);
            //设置accept接收阻塞时间
            serverSocket.setSoTimeout(SO_TIMEOUT);

            System.out.println("server listen 9090 start.");

            while (true) {
                final Socket client = serverSocket.accept();
                //超时
                client.setKeepAlive(CLI_KEEPALIVE);
                //是否支持紧急的消息设置
                client.setOOBInline(CLI_OOB);
                //recivebuffer
                client.setReceiveBufferSize(CLI_REC_BUF);
                //sendbuffer
                client.setSendBufferSize(CLI_SEND_BUF);
                //read阻塞超时
                client.setSoTimeout(CLI_TIMEOUT);
                //tcp延迟设置
                client.setTcpNoDelay(CLI_NO_DELAY);
                //存活设置
                client.setSoLinger(CLI_LINGER, CLI_LINGER_N);

                System.out.println("client " + client.getInetAddress() + ":" + client.getPort());

                //接收到clien请求,新起一个线程处理
                new Thread(() -> {
                    try {
                        //自旋获取输入内容
                        while (true) {
                            final InputStream inputStream = client.getInputStream();
                            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            char[] bufferbytes = new char[RECEIVE_BUFFER];
                            int read = 0;
                            while ((read = bufferedReader.read(bufferbytes)) > 0) {
                                System.out.println("client read some data is :" + read + " val :" + new String(bufferbytes, 0, read));
                            }
                            if (read == 0) {
                                System.out.println("client readed nothing!");
                            } else {
                                System.out.println("client readed -1...");
                                client.close();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
