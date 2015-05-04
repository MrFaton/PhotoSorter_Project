package com.mr_faton.handler;

import com.mr_faton.StartProgram;
import com.mr_faton.panel.CenterPanel;
import com.mr_faton.statements.Constants;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Mr_Faton on 04.05.2015.
 */
public class HandleButtonHandler {
    public void handle() {
        try {
            saveDirs();
        } catch (IllegalArgumentException ex) {
            //одно из полей пустое. нельзя продолжать работу.
            return;
        }
    }

    private void saveDirs() throws IllegalArgumentException{
        CenterPanel centerPanel = CenterPanel.getInstance();
        String inputDirPath = centerPanel.getInputDir();
        if (inputDirPath == null || inputDirPath.length() <= 1) {
            showErrorMessage("Поле папки-источкика незаполнено!\nВыбери папку-источник.");
            throw new IllegalArgumentException();
        }
        String outputDirPath = centerPanel.getOutputDir();
        if (outputDirPath == null || outputDirPath.length() <= 1) {
            showErrorMessage("Поле результирующей папки незаполено!\nВыбери результирующую папку.");
            throw new IllegalArgumentException();
        }

        Properties properties = new Properties();
        properties.put(Constants.KEY_inputDir, inputDirPath);
        properties.put(Constants.KEY_outputDir, outputDirPath);
        try {
            properties.store(new FileOutputStream(Constants.FILE_settings_file), "Directories");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(StartProgram.mainFrame, message, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }
}
