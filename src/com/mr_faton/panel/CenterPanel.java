package net.mr_faton.Different_Things.photo_sorter.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

    public static CenterPanel getInstance() {
        if (centerPanel == null) {
            centerPanel = new CenterPanel();
        }
        return centerPanel;
    }

    public CenterPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));
        dirChooser = new JFileChooser();
        dirChooser.setCurrentDirectory(new File("."));
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        dirChooser.setAcceptAllFileFilterUsed(false);//disable the "All files" option.

        inputDirLabel = new JLabel("Папка из которой берём файлы:", JLabel.LEFT);
        outputDirLabel = new JLabel("Корневая папка альбома:", JLabel.LEFT);

        inputDirField = new JTextField();
        outputDirField = new JTextField();

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

        panel.add(inputDirLabel);
        panel.add(new JLabel());
        panel.add(inputDirField);
        panel.add(openInputButton);
        panel.add(outputDirLabel);
        panel.add(new Label());
        panel.add(outputDirField);
        panel.add(openOutputButton);
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
