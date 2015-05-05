package com.mr_faton.handler;

import com.mr_faton.StartProgram;
import com.mr_faton.panel.CenterPanel;
import com.mr_faton.panel.SouthPanel;
import com.mr_faton.statements.Constants;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by Mr_Faton on 04.05.2015.
 */
public final class HandleButtonHandler {
    private CenterPanel centerPanel;
    private SouthPanel southPanel;
    private List<File> fileList;
    private BasicFileAttributes fileAttributes;
    private String sourceDir;
    private String destDir;
    private String[] months;

    public HandleButtonHandler() {
        centerPanel = CenterPanel.getInstance();
        southPanel = SouthPanel.getInstance();
        fileList = new LinkedList<File>();
        months = new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь",
                "Октябрь", "Ноябрь", "Декабрь"};
    }

    public void handle() {
        try {
            saveDirs();
            southPanel.setProgressBarVisible(true);
            collectAllFiles(new File(sourceDir));
            for (File file : fileList) {
                Map<String, String> fileNameAndFileDirMap = getFileNameAndFileDir(file);
            }

        } catch (IllegalArgumentException ex) {
            //одно из полей пустое. нельзя продолжать работу.
            return;
        }
    }


    private void saveDirs() throws IllegalArgumentException {
        sourceDir = centerPanel.getInputDir();
        if (sourceDir == null || sourceDir.length() <= 1) {
            showErrorMessage("Поле папки-источкика незаполнено!\nВыбери папку-источник.");
            throw new IllegalArgumentException();
        }
        destDir = centerPanel.getOutputDir();
        if (destDir == null || destDir.length() <= 1) {
            showErrorMessage("Поле результирующей папки незаполено!\nВыбери результирующую папку.");
            throw new IllegalArgumentException();
        }

        Properties properties = new Properties();
        properties.put(Constants.KEY_inputDir, sourceDir);
        properties.put(Constants.KEY_outputDir, destDir);
        try {
            properties.store(new FileOutputStream(Constants.FILE_settings_file), "Directories");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectAllFiles(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            } else {
                //значит это папка, заходим в неё
                collectAllFiles(file);
            }
        }
    }

    private Map<String, String> getFileNameAndFileDir(File file) {
        StringBuilder pathToFile;
        Calendar lastModifiedDate;
        Map<String, String> fileNameAndFileDirMap;
        String day;
        String month;
        String year;
        String hour;
        String minute;
        String second;
        String fileDir;
        String fileName;

        try {
            fileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            ///1111111111111111111111111111
            e.printStackTrace();
        }
        pathToFile = new StringBuilder();
        pathToFile.append(sourceDir + "\\");

        lastModifiedDate = new GregorianCalendar();
        lastModifiedDate.setTimeInMillis(fileAttributes.lastModifiedTime().toMillis());
        day = lastModifiedDate.get(Calendar.DAY_OF_MONTH) + "";
        month = lastModifiedDate.get(Calendar.MONTH) + "";
        year = lastModifiedDate.get(Calendar.YEAR) + "";
        hour = lastModifiedDate.get(Calendar.HOUR) + "";
        minute = lastModifiedDate.get(Calendar.MINUTE) + "";
        second = lastModifiedDate.get(Calendar.SECOND) + "";


        return null;
    }

    private void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(StartProgram.mainFrame, message, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int position = name.lastIndexOf('.');
        if (position > 0) {
            return name.substring(position + 1);
        }
        return "";
    }

    private String getMonthDir(String monthStr) {
        if (monthStr != null && monthStr.length() != 0) {
            int month = Integer.valueOf(monthStr);
            ////
            return "";
        } else {
            return "";
        }
    }
}

class Test {
    public static void main(String[] args) {
    }
}