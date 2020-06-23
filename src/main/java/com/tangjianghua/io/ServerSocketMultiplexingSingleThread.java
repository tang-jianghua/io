package com.tangjianghua.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class ServerSocketMultiplexingSingleThread {


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

    static Selector selector;
    public static void m() {
        try (final ServerSocketChannel open = ServerSocketChannel.open();
             ) {
            //多路复用模式，select函数 在内核，可以分为select/poll和epoll
            //在java语言中抽象为Selector
            //linux jvm默认选择epoll，可以通过
            //-Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider
           // -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider
            //来选择
            selector = Selector.open();
            //绑定一个端口
            open.bind(new InetSocketAddress(9090), BACK_LOG);
            //非阻塞
            open.configureBlocking(false);
            //将socket注册为多路复用模式，并监听accept函数
            open.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server listen 9090 start.");
            final LinkedList<SocketChannel> socketClients = new LinkedList<>();
            while (true) {
                final Set<SelectionKey> keys = selector.keys();
                System.out.println("keys:" + keys.size());
                //当select返回的keys数量大于0时
                while (selector.select() > 0) {
//                    获取有状态的key
                    //这个keys，在poll中是维护的数组，在内核态依然是O(N)的时间复杂度；在epoll中维护的是红黑树+链表
                    //时间复杂度是O(1)-O(log2)
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    //便利keys，无论是epoll还是poll，最后都逃不过遍历数组
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        System.out.println("测一下有没有remove掉："+key.toString());
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isWritable()) {
                            writeHandler(key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(selector.isOpen()){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeHandler(SelectionKey key) {
        final SocketChannel channel = (SocketChannel) key.channel();
        final ByteBuffer attachment = (ByteBuffer) key.attachment();
        attachment.flip();
        while (attachment.hasRemaining()){
            try {
                channel.write(attachment);
            } catch (IOException e) {
                e.printStackTrace();
            }
            attachment.clear();
            key.cancel();
        }
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readHandler(SelectionKey key) {
        final SocketChannel channel = (SocketChannel) key.channel();
        final ByteBuffer attachment = (ByteBuffer) key.attachment();
        attachment.clear();
        int read=0;
        while (true){
            try {
                if (!((read=channel.read(attachment))>0)) break;
                attachment.flip();
                while (attachment.hasRemaining()){
                    channel.write(attachment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void acceptHandler(SelectionKey key) {
        final ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        try {
            final SocketChannel accept = channel.accept();

            accept.configureBlocking(false);
            accept.register(selector,SelectionKey.OP_READ,ByteBuffer.allocateDirect(4096));

            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + accept.getRemoteAddress());
            System.out.println("-------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
