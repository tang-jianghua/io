package com.tangjianghua.io;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author tangjianghua
 * @date 2020/7/3
 */
public class Test {
    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put("a",1);
        final TreeMap<Object, Object> treeMap = new TreeMap<>();
        treeMap.put("a",1);
        treeMap.put("b",1);
    }
}
