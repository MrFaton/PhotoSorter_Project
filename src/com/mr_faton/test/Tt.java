package com.mr_faton.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

/**
 * Created by root on 04.05.2015.
 */
public class Tt {
    public static void main(String[] args) throws Exception{
//        File file = new File("C:\\qq\\ww.jpg");
        File file = new File("C:\\qq\\ee.mp4");
        Map<String, Object> stringObjectMap = Files.readAttributes(file.toPath(), "*");
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
/*
lastModifiedTime=2014-12-07T16:41:55Z
 */