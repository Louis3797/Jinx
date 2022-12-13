package org.jinx.view;

import org.jinx.login.DatabaseLoginManager;
import org.jinx.login.ILoginManager;
import org.jinx.presenter.LoginPresenter;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int width = 1100;
    private static final int height = 700;

    public static final Dimension dimension = new Dimension(width, height);

    public static final CardLayout cardLayout = new CardLayout();
    public static final JPanel mainPanel = new JPanel();

    public MainFrame() throws HeadlessException {
        super.setTitle("Jinx");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);

        // main panel
        mainPanel.setSize(dimension);
        mainPanel.setLayout(cardLayout);
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setVisible(true);

        JPanel loginAndPlayerManager = new JPanel();

        ILoginManager loginManager = new DatabaseLoginManager();
        LoginView loginView = new LoginView();
        LoginPresenter presenter = new LoginPresenter(loginView, loginManager);


        loginAndPlayerManager.setSize(dimension);
        loginAndPlayerManager.setLayout(new GridLayout(0, 2));
        loginAndPlayerManager.add(loginView);


        mainPanel.add(loginAndPlayerManager, "Login");

        cardLayout.show(mainPanel, "Login");
        add(mainPanel);


        setVisible(true);
    }
}
