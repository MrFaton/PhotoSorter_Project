package com.mr_faton.panel;

import com.mr_faton.handler.HandleButtonHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mr_Faton on 04.05.2015.
 */
public class SouthPanel {
    private static SouthPanel southPanel;
    private JPanel panel;
    private JButton handleButton;
    private JProgressBar progressBar;

    public static SouthPanel getInstance() {
        if (southPanel == null) {
            southPanel = new SouthPanel();
        }
        return southPanel;
    }

    private SouthPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 0, 10));
        Border progressBorder = BorderFactory.createTitledBorder("Прогресс обработки");
        progressBar = new JProgressBar();
        progressBar.setBorder(progressBorder);
        progressBar.setStringPainted(true);//для отображения прогресса числом
        progressBar.setVisible(false);//чтобы при старте был невидимым

        handleButton = new JButton("Обработать");
        handleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HandleButtonHandler handleButtonHandler = new HandleButtonHandler();
                        handleButtonHandler.handle();
                    }
                }).start();
            }
        });

        //если сделать так, то кнопка не будет растянутой
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(handleButton);

        panel.add(progressBar);
        panel.add(handleButton);
//        panel.add(buttonPanel);//добавляет на панель новую панель, всё для того же, чтобы не была растянута кнопка
    }

    public void setProgressBarVisible(boolean isVisible) {
        progressBar.setVisible(isVisible);
    }

    public void setProgressBarValue(int value) {
        progressBar.setValue(value);
    }

    public JPanel getPanel() {
        return panel;
    }
}
