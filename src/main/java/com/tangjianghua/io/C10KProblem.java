package com.tangjianghua.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class C10KProblem {

    public static void main(String[] args) {
        for (int i = 10000; i < 65000; i++) {
            try(final SocketChannel open = SocketChannel.open()){
                open.bind(new InetSocketAddress(i));
                open.connect(new InetSocketAddress("192.168.186.1",9090));
            }catch (IOException e){

            }
        }
    }
}
