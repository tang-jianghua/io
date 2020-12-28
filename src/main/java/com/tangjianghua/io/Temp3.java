package com.tangjianghua.io;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tangjianghua
 * @date 2020/12/18
 */
public class Temp3 {


    public volatile static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        File file = new File("D:\\temp1");
        File file2 = new File("D:\\test2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        File[] files = file.listFiles();
        ConcurrentHashMap<String,Data> concurrentHashMap = new ConcurrentHashMap<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

        List< Future<ConcurrentHashMap> > list = new ArrayList<>();
        for (File file1 : files) {
            Future<ConcurrentHashMap> future = threadPoolExecutor.submit(new Task(file1));
            list.add(future);
        }

        while (atomicInteger.get() < files.length) {

        }
        for (Future<ConcurrentHashMap> future:list) {
            ConcurrentHashMap map = future.get();
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String)iterator.next();
                Data data = (Data)map.get(key);
                if(concurrentHashMap.contains(key)){
                    Data data1 = concurrentHashMap.get(key);
                    data1.addAmount(data.getAmount().longValue());
                    data1.addCount(data.getCount().longValue());
                    concurrentHashMap.put(key,data1);
                }else{
                    concurrentHashMap.put(key,data);
                }
            }
        }
        Iterator<String> iterator = concurrentHashMap.keySet().iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            Data data = concurrentHashMap.get(next);

            StringBuilder stringBuilder = new StringBuilder();
     /*       stringBuilder.append("INSERT INTO test VALUES ('");
            stringBuilder.append(next);
            stringBuilder.append("',");
            stringBuilder.append(data.getCount().get());
            stringBuilder.append(",");
            stringBuilder.append(data.getAmount().get());
            stringBuilder.append(");");*/
            stringBuilder.append("|");
            stringBuilder.append(next);
            stringBuilder.append("|");
            stringBuilder.append(data.getCount().get());
            stringBuilder.append("|");
            stringBuilder.append(data.getAmount().get());
            stringBuilder.append("|\r\n");
            //System.out.println(stringBuilder);
            fileOutputStream.write(stringBuilder.toString().getBytes());
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        threadPoolExecutor.shutdown();
    }
}
