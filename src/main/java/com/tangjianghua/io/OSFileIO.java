package com.tangjianghua.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class OSFileIO {

    static byte[] data = "123456789\n".getBytes();
    //static String path = "d:/file/io/out.txt";
    static String path = "/home/tangjianghua/io/out.txt";

    public static void main(String[] args) {
        whatIsByteBuffer();
     /*   final Scanner scanner = new Scanner(System.in);
        final String next = scanner.next();
        switch (next) {
            case "0":
                traditionalIO();
            case "1":
                bufferedIO();
            case "3":
                newIO2();
            case "4":
                newIO();
        }*/
    }

    public static void traditionalIO() {

        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            while (true) {
                Thread.sleep(1000);
                fileOutputStream.write(data);
            }
//            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void bufferedIO() {

        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)
        ) {
            while (true) {
                bufferedOutputStream.write(data);
            }
//            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newIO() {
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
            final FileChannel channel = randomAccessFile.getChannel();
            final long length = randomAccessFile.length();
            System.out.println("fileLength:" + length);
            System.out.println("dataLength:" + data.length);
            final MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, length, data.length);
            mappedByteBuffer.put(data);
            System.out.println("map--put----3----");
            System.in.read();

            final ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) randomAccessFile.length());

            randomAccessFile.seek(0);
            int read = channel.read(byteBuffer);
            System.out.println(byteBuffer);
            byteBuffer.flip();
            System.out.println(byteBuffer);
            for (int i = 0; i < byteBuffer.limit(); i++) {
                System.out.print((char) byteBuffer.get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newIO2() {
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
            final FileChannel channel = randomAccessFile.getChannel();
            final MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, Integer.MAX_VALUE);

            while (true) {
                mappedByteBuffer.put(data);
            }
           /* System.out.println("map--put----3----");
            System.in.read();

            final ByteBuffer byteBuffer = ByteBuffer.allocateDirect( (int)randomAccessFile.length());

            randomAccessFile.seek(0);
            int read = channel.read(byteBuffer);
            System.out.println(byteBuffer);
            byteBuffer.flip();
            System.out.println(byteBuffer);
            for (int i = 0; i < byteBuffer.limit(); i++) {
                System.out.print((char) byteBuffer.get());
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void whatIsByteBuffer() {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("position:" + byteBuffer.position());
        System.out.println("limit:" + byteBuffer.limit());
        System.out.println("capacity:" + byteBuffer.capacity());
        System.out.println("mark:" + byteBuffer.mark());


        byteBuffer.put("123".getBytes());
        System.out.println("-------------put:123......");
        System.out.println("mark:" + byteBuffer.mark());

        byteBuffer.flip();
        System.out.println("-------------flip......");
        System.out.println("mark:" + byteBuffer.mark());

        final byte b = byteBuffer.get();
        System.out.println("-------------get......");
        System.out.println("mark:" + byteBuffer.mark());
        System.out.println("bytes:" + (char) b);

        byteBuffer.compact();
        System.out.println("-------------compact......");
        System.out.println("mark:" + byteBuffer.mark());

        byteBuffer.flip();
        System.out.println("-------------flip......");
        System.out.println("mark:" + byteBuffer.mark());

        byteBuffer.clear();
        System.out.println("-------------clear......");
        System.out.println("mark:" + byteBuffer.mark());

    }
}
