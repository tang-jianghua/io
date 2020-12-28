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
public class Temp {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\temp");
        File file2 = new File("D:\\temp.sql");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        File[] files = file.listFiles();

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (File file1 : files) {
            System.out.println("filename:" + file1.getName());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
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
                    stringBuilder.append("');");
                    //System.out.println(stringBuilder);
                    fileOutputStream.write(stringBuilder.toString().getBytes());
                }
                line = null;
            }
            System.out.println("filename:" + file1.getName()+"done:"+atomicInteger.incrementAndGet());
            bufferedReader.close();
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
