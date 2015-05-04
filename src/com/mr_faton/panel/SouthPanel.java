package com.mr_faton.panel;

import com.mr_faton.handler.HandleButtonHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mr_Faton on 04.05.2015.
 */
public class SouthPanel {
    private static SouthPanel southPanel;
    private JPanel panel;
    private JButton handleButton;

    public static SouthPanel getInstance() {
        if (southPanel == null) {
            southPanel = new SouthPanel();
        }
        return southPanel;
    }

    private SouthPanel() {
        panel = new JPanel();

        handleButton = new JButton("Обработать");
        handleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HandleButtonHandler handleButtonHandler = new HandleButtonHandler();
                handleButtonHandler.handle();
            }
        });

        panel.add(handleButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
