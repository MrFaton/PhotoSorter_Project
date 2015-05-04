package com.mr_faton.panel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mr_Faton on 04.05.2015.
 */
public class NorthPanel {
    private static NorthPanel northPanel;
    private JPanel panel;
    private JLabel welcomeLabel;

    public static NorthPanel getInstance() {
        if (northPanel == null) {
            northPanel = new NorthPanel();
        }
        return northPanel;
    }

    private NorthPanel() {
        panel = new JPanel(new BorderLayout());
        welcomeLabel = new JLabel("<html>" +
                "<h2 style=\"text-align: center;\">Универсальный сортировщик и переименовщик файлов!</h2>\n" +
                "<ul>\n" +
                "\t<li>файлы будут переименованы по такому шаблону: день-месяц-год(часы-минуты-секунды). \n" +
                "Например: 04-05-2015(11-21-40).jpg</li>\n" +
                "\t<li>файлы будут сортированы таким образом:\n" +
                "Папки: &quot;2015&quot; =&gt; &quot;05 Май&quot; =&gt; &quot;04-05-2015(11-21-40).jpg&quot;</li>\n" +
                "</ul>\n" +
                "</html>");

        panel.add(welcomeLabel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }
}
