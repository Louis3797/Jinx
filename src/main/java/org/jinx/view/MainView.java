package org.jinx.view;

import javax.swing.*;
import java.awt.*;

/**
 * MainView is the JFrame that holds all Views
 */
public class MainView extends JFrame {

    private static final int width = 1100;
    private static final int height = 700;

    public static final Dimension dimension = new Dimension(width, height);

    public static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel();

    public MainView() throws HeadlessException {
        super.setTitle("Jinx");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);

        // main panel
        mainPanel.setSize(dimension);
        mainPanel.setLayout(cardLayout);
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setVisible(true);



//        mainPanel.add(, "Login");

        cardLayout.show(mainPanel, "Login");
        add(mainPanel);


        setVisible(true);
    }
}
