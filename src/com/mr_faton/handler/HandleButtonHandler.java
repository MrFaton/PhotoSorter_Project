package com.mr_faton.handler;

import com.mr_faton.StartProgram;
import com.mr_faton.panel.CenterPanel;
import com.mr_faton.panel.SouthPanel;
import com.mr_faton.statements.Constants;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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
    private List<File> duplicatesFileList;
    private BasicFileAttributes fileAttributes;
    private String sourceDir;
    private String destDir;
    private String[] months;
    private int totalFoundFiles;
    private int totalCopiedFiles;

    public HandleButtonHandler() {
        centerPanel = CenterPanel.getInstance();
        southPanel = SouthPanel.getInstance();
        months = new String[]{"", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь",
                "Октябрь", "Ноябрь", "Декабрь"};
    }

    public void handle() {
        totalFoundFiles = 0;
        totalCopiedFiles = 0;
        try {
            saveDirs();
            fileList = new LinkedList<>();
            duplicatesFileList = new LinkedList<>();
            southPanel.setProgressBarVisible(true);
            collectAllFiles(new File(sourceDir));
            for (File sourceFile : fileList) {
                Map<String, String> fileNameAndFileDirMap = getFileNameAndFileDir(sourceFile);
                String destFileDir = fileNameAndFileDirMap.get(Constants.KEY_filePath);
                String destFileName = fileNameAndFileDirMap.get(Constants.KEY_fileName);
                new File(destFileDir).mkdirs();

                copyFile(sourceFile, destFileDir, destFileName);
                updateProgressBar();
            }

            System.out.println("Done");
            System.out.println("totalFoundFiles=" + totalFoundFiles);
            System.out.println("totalCopiedFiles=" + totalCopiedFiles);
            System.out.println("duplicates=" + duplicatesFileList.size());
            System.out.println("duplicates Files =" + duplicatesFileList);

        } catch (IllegalArgumentException ex) {
            //NOP
            /*
            обработка находится в методе saveDirs() - там выводится сообщение что поля с папками пустые. Этот кэч
            добавляется для того, чтобы никак не риагировать на пустрое поле и прервать выполниение метода обработки
             */
            return;
        } catch (IOException ex) {
            showErrorMessage("Возникла ошибка!\n" + ex.getMessage());
            ex.printStackTrace();
            return;
        }
    }


    private void saveDirs() throws IOException {
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
        properties.store(new FileOutputStream(Constants.FILE_settings_file), "Directories");
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
        totalFoundFiles = fileList.size();
    }

    private Map<String, String> getFileNameAndFileDir(File file) throws IOException{
        StringBuilder pathToFile;
        Calendar lastModifiedDate;
        Map<String, String> fileNameAndFileDirMap;
        String day;
        String month;
        String montDirName;
        String year;
        String hour;
        String minute;
        String second;
        String fileExtension;
        String fileDir;
        String fileName;

        fileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        pathToFile = new StringBuilder();
        pathToFile.append(sourceDir + "\\");

        lastModifiedDate = new GregorianCalendar();
        lastModifiedDate.setTimeInMillis(fileAttributes.lastModifiedTime().toMillis());
        day = String.format("%td", lastModifiedDate);
        month = String.format("%tm", lastModifiedDate);
        montDirName = getMonthDir(month);
        year = String.format("%tY", lastModifiedDate);
        hour = String.format("%tH", lastModifiedDate);
        minute = String.format("%tM", lastModifiedDate);
        second = String.format("%tS", lastModifiedDate);

        fileExtension = getFileExtension(file);

        fileDir = destDir + "\\" + year + "\\" + montDirName;
        fileName = day + '-' + month + '-' + year + '(' + hour + '-' + minute + '-' + second + ")." + fileExtension;

        fileNameAndFileDirMap = new HashMap<>(3, 1);
        fileNameAndFileDirMap.put(Constants.KEY_filePath, fileDir);
        fileNameAndFileDirMap.put(Constants.KEY_fileName, fileName);

        return fileNameAndFileDirMap;
    }

    private void copyFile(File sourceFile, String destFileDir, String destFileName) throws IOException {
        String destFilePath = destFileDir + "\\" + destFileName;
        File destFile = new File(destFilePath);
        try {
            Files.copy(sourceFile.toPath(), destFile.toPath());
            totalCopiedFiles++;
        } catch (FileAlreadyExistsException existingEx) {
            duplicatesFileList.add(destFile);
            StringBuilder changedFileName = new StringBuilder(destFileName);
            int position = changedFileName.indexOf(".");
            if (position < 0) {
                throw new IOException("У файла отсутствует раширение!\n" + destFilePath);
            }
            changedFileName.insert(position, "(копия)");
            copyFile(sourceFile, destFileDir, changedFileName.toString());
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int position = name.lastIndexOf('.');
        if (position > 0) {
            return name.substring(position + 1).toLowerCase();
        }
        return "";
    }

    private String getMonthDir(String monthStr) throws IOException{
        if (monthStr != null && monthStr.length() != 0) {
            int month = Integer.valueOf(monthStr);
            String monthName = months[month];
            String monthDir = monthStr + " " + monthName;
            return monthDir;
        } else {
            throw new IOException("Метод getMonthDir(String monthStr) в качесте аргумента получил плохое значение!\n" +
                    "monthStr=" + monthStr);
        }
    }

    private void updateProgressBar() {
        int status = (int)Math.round((double)totalCopiedFiles / totalFoundFiles * 100);
        southPanel.setProgressBarValue(status);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(StartProgram.mainFrame, message, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }
}

class Test {
    public static void main(String[] args) {
    }
}