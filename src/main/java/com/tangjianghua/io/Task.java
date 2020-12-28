package com.tangjianghua.io;

import java.io.*;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangjianghua
 * @date 2020/12/18
 */
public class Task implements Callable<ConcurrentHashMap> {

    private File file;

    public Task(File file) {
        this.file = file;
    }

    @Override
    public ConcurrentHashMap call() throws Exception {
        ConcurrentHashMap<String, Data> concurrentHashMap = new ConcurrentHashMap<>();
        System.out.println("filename:" + file.getName());
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
                String merchantId = split[1].trim();
                Long count = Long.valueOf(split[2].trim());
                Long amount = Long.valueOf(split[3].trim());
                Data data = concurrentHashMap.contains(merchantId) ? concurrentHashMap.get(merchantId) : new Data();
                data.addCount(count);
                data.addAmount(amount);
                concurrentHashMap.put(merchantId, data);
            }
            line = null;
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done:" + file.getName() + ":" + Temp3.atomicInteger.incrementAndGet());
        return concurrentHashMap;
    }
}
