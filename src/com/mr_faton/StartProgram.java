package com.mr_faton;

import com.mr_faton.fraim.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mr_Faton on 03.05.2015.
 */
public class StartProgram {
    public static JFrame mainFrame;

    public StartProgram() {
    }

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.setTitle("Tro-lo-lo");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setVisible(true);
            }
        });
    }
}
