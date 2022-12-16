package org.jinx.view;

import org.jinx.game.PlayerManager;
import org.jinx.game_state.GameState;
import org.jinx.highscore.HighScoreList;
import org.jinx.presenter.*;

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

    public static final JPanel loginAndPlayerPanel = new JPanel();
    public static GameState gameState = new GameState();


    private HighScoreList highScoreList = new HighScoreList();
    private PlayerManager playerManager;


    public MainView() throws HeadlessException {
        super.setTitle("Jinx");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);
        setResizable(false);

        playerManager = PlayerManager.getPlayerManagerInstance();

        // main panel
        mainPanel.setSize(dimension);
        mainPanel.setLayout(cardLayout);
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setVisible(true);

        GameView gameView = new GameView();

        StartView startView = new StartView();
        StartPresenter startPresenter = new StartPresenter(startView, gameState);

        LoginView loginView = new LoginView();
        LoginPresenter loginPresenter = new LoginPresenter(loginView, playerManager);

        RegisterView registerView = new RegisterView();
        RegisterPresenter registerPresenter = new RegisterPresenter(registerView, gameState);

        HighscoreView highscoreView = new HighscoreView();
        HighscorePresenter highscorePresenter = new HighscorePresenter(highscoreView, highScoreList);





        mainPanel.add(loginView, Views.Login.name());

        mainPanel.add(startView, Views.Start.name());

        mainPanel.add(registerView, Views.Register.name());

        mainPanel.add(highscoreView, Views.HighScore.name());

        mainPanel.add(gameView, Views.Game.name());

        cardLayout.show(mainPanel, Views.Start.name());

        add(mainPanel);

        setVisible(true);
    }

}
