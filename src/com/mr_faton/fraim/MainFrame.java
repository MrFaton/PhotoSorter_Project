package com.mr_faton.fraim;

import com.mr_faton.panel.CenterPanel;
import com.mr_faton.panel.NorthPanel;
import com.mr_faton.panel.SouthPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mr_Faton on 03.05.2015.
 */
public final class MainFrame extends JFrame {
    private static int MAIN_FRAME_WIDTH = 465;
    private static int MAIN_FRAME_HEIGHT = 420;


    public MainFrame() throws HeadlessException {
        //получаем разрешение монитора для того, чтобы выводить наш фрейм по центру
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension monitorScreenSize = toolkit.getScreenSize();
        int monitorWidth = monitorScreenSize.width;
        int monitorHeight = monitorScreenSize.height;

//        //устанавливаем размер
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
//        //устанавливаем положение (координаты фрейма)
        setLocation(monitorWidth / 2 - MAIN_FRAME_WIDTH / 2, monitorHeight / 2 - MAIN_FRAME_HEIGHT / 2);
//        //делаем чтобы окно не могло менять размер
//        setResizable(false);

        //внутри пакета храниться иконка фрейма, этот блок нужен чтобы получить к ней путь (получить иконку)
//        URL iconURL = getClass().getResource("/com/mr_faton/resources/frameIcon.png");
//        Image frameIcon = new ImageIcon(iconURL).getImage();
//        setIconImage(frameIcon);

        add(NorthPanel.getInstance().getPanel(), BorderLayout.NORTH);
        add(CenterPanel.getInstance().getPanel(), BorderLayout.CENTER);
        add(SouthPanel.getInstance().getPanel(), BorderLayout.SOUTH);

    }
}
/*
Основной фрейм программы
 */
