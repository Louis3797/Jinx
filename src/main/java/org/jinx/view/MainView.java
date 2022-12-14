package org.jinx.view;

import org.jinx.presenter.StartPresenter;

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
    public static final JPanel mainPanel = new JPanel();

    public MainView() throws HeadlessException {
        super.setTitle("Jinx");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);

        // main panel
        mainPanel.setSize(dimension);
        mainPanel.setLayout(cardLayout);
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setVisible(true);

        StartView startView = new StartView();
        StartPresenter startPresenter = new StartPresenter(startView, null);

        LoginView loginView = new LoginView();

        mainPanel.add(loginView, Views.Login.name());

        mainPanel.add(startView, Views.Start.name());



        cardLayout.show(mainPanel, Views.Start.name());
        add(mainPanel);


        setVisible(true);
    }
}
