package com.mr_faton.panel;

import com.mr_faton.statements.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Mr_Faton on 03.05.2015.
 */
public final class CenterPanel extends JPanel {
    private static CenterPanel centerPanel;
    private JPanel panel;
    private JLabel inputDirLabel;
    private JLabel outputDirLabel;
    private JTextField inputDirField;
    private JTextField outputDirField;
    private JButton openInputButton;
    private JButton openOutputButton;
    private JFileChooser dirChooser;
    private String inputDirPath;
    private String outputDirPath;

    public static CenterPanel getInstance() {
        if (centerPanel == null) {
            centerPanel = new CenterPanel();
        }
        return centerPanel;
    }

    private CenterPanel() {
        panel = new JPanel(new GridLayout(2, 1, 0, 15));

        dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dirChooser.setAcceptAllFileFilterUsed(false);//disable the "All files" option.

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(Constants.FILE_settings_file));
            inputDirPath = properties.getProperty(Constants.KEY_inputDir);
            outputDirPath = properties.getProperty(Constants.KEY_outputDir);
            if (inputDirPath == null || outputDirPath == null || inputDirPath.length() < 1 || outputDirPath.length() < 1) {
                throw new IOException();
            }
            dirChooser.setCurrentDirectory(new File(outputDirPath));
        } catch (IOException e) {
            //значит файла с настройками нет
            inputDirPath = "";
            outputDirPath = "";
            dirChooser.setCurrentDirectory(new File("."));
        }

        inputDirLabel = new JLabel("Папка из которой берём файлы:", JLabel.LEFT);
        outputDirLabel = new JLabel("Корневая папка альбома:", JLabel.LEFT);

        inputDirField = new JTextField(inputDirPath);
        outputDirField = new JTextField(outputDirPath);

        openInputButton = new JButton("Открыть");
        openInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = chooseFolder("Выбери папку из которой берём файлы");
                if (path != null) {
                    inputDirField.setText(path);
                }
            }
        });

        openOutputButton = new JButton("Открыть");
        openOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = chooseFolder("Выбери корневую папку, куда будем сохранять");
                if (path != null) {
                    outputDirField.setText(path);
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        JPanel inputFieldAndButtonPanel = new JPanel();
        inputFieldAndButtonPanel.setLayout(new BorderLayout(7, 0));
        inputFieldAndButtonPanel.add(inputDirField, BorderLayout.CENTER);
        inputFieldAndButtonPanel.add(openInputButton, BorderLayout.EAST);
        inputPanel.add(inputDirLabel);
        inputPanel.add(inputFieldAndButtonPanel);

        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        JPanel outputFieldAndButtonPanel = new JPanel();
        outputFieldAndButtonPanel.setLayout(new BorderLayout(7, 0));
        outputFieldAndButtonPanel.add(outputDirField, BorderLayout.CENTER);
        outputFieldAndButtonPanel.add(openOutputButton, BorderLayout.EAST);
        outputPanel.add(outputDirLabel);
        outputPanel.add(outputFieldAndButtonPanel);

        panel.add(inputPanel);
        panel.add(outputPanel);
    }

    private String chooseFolder(String title) {
        String pathTOFolder;
        dirChooser.setDialogTitle(title);
        int result = dirChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            pathTOFolder = dirChooser.getSelectedFile().getPath();
            return pathTOFolder;
        } else {
            return null;
        }
    }

    public String getInputDir() {
        return inputDirField.getText();
    }

    public String getOutputDir() {
        return outputDirField.getText();
    }

    public JPanel getPanel() {
        return panel;
    }
}
