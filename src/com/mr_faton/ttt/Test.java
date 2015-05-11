package com.mr_faton.ttt;

import be.pw.jexif.JExifInfo;
import be.pw.jexif.JExifTool;
import be.pw.jexif.exception.ExifError;
import be.pw.jexif.exception.JExifException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by root on 11.05.2015.
 */
public class Test {
    public static void main(String[] args) throws IOException, JExifException, ExifError {
        File exifToolFile = new File("resources\\exiftool.exe");
        String pathToExiftool = exifToolFile.getAbsolutePath();
        System.setProperty("exiftool.path", pathToExiftool);
        JExifTool exifTool = new JExifTool();
//        JExifInfo info = exifTool.getInfo(new File("X:\\Faton\\photo1.jpg"));
        JExifInfo info = exifTool.getInfo(new File("X:\\Faton\\video1.mov"));
        showAllExifTags(info);
        exifTool.stop();
    }

    public static void showAllExifTags(JExifInfo info) throws JExifException, ExifError {
        Map<String, String> allTagsValues = info.getAllTagsValues();
        for (Map.Entry<String, String> entry : allTagsValues.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
//        if (allTagsValues.containsKey("Model")) {
//            System.out.println("Model = " + allTagsValues.get("Model"));
//        }
    }
}
