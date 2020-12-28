package com.tangjianghua.io;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangjianghua
 * @date 2020/12/18
 */
public class Temp2 {
    volatile static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\temp");
        File file2 = new File("D:\\temp.sql");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        File[] files = file.listFiles();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));


        for (File file1 : files) {
            threadPoolExecutor.submit(() -> {
                System.out.println("filename:" + file1.getName());
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String line = null;
                while (true) {
                    try {
                        if (!((line = bufferedReader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (
                            line.startsWith("|")
                                    && !line.contains("mid")
                    ) {
                        String[] split = line.split("\\|");
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("INSERT INTO test VALUES ('");
                        stringBuilder.append(split[1].trim());
                        stringBuilder.append("','");
                        stringBuilder.append(split[2].trim());
                        stringBuilder.append("','");
                        stringBuilder.append(split[3].trim());
                        stringBuilder.append("');\r\n");
                        //System.out.println(stringBuilder);
                        try {
                            fileOutputStream.write(stringBuilder.toString().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    line = null;
                }
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("done:" + file1.getName() + ":" + atomicInteger.incrementAndGet());
            });

        }
        while (atomicInteger.get() < files.length) {

        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
