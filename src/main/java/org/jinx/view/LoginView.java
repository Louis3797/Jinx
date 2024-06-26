/*
 * Created by JFormDesigner on Wed Dec 14 14:23:57 CET 2022
 */

package org.jinx.view;

import org.jinx.game.PlayerManager;
import org.jinx.player.AgentDifficulty;
import org.jinx.presenter.PlayerManagerPresenter;
import org.jinx.presenter.interfaces.ILoginPresenter;
import org.jinx.swing.RoundedBorder;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.ILoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author unknown
 */
public class LoginView extends JPanel implements ILoginView {


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel title;
    private JLabel playerNameLabel;
    private JLabel passwordLabel;
    private JLabel status;
    private JLabel difficultyLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JRadioButton difficultyNoRadioButton;
    private JRadioButton difficultyEasyRadioButton;
    private JRadioButton difficultyMediumRadioButton;
    private JRadioButton difficultyHardRadioButton;
    private JLabel registerLabel;
    private JButton loginButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    private PlayerManagerView playerManagerView;

    private ILoginPresenter presenter;

    private PlayerManagerPresenter playerManagerPresenter;

    private PlayerManager playerManager;

    /**
     * Presenter for the View
     */
    public LoginView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        playerManager = PlayerManager.getPlayerManagerInstance();

        playerManagerView = new PlayerManagerView();
        playerManagerPresenter = new PlayerManagerPresenter(playerManagerView,playerManager);

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        title = new JLabel();
        playerNameLabel = new JLabel();
        passwordLabel = new JLabel();
        status = new JLabel();
        difficultyLabel = new JLabel();
        passwordField = new JPasswordField();
        usernameField = new JTextField();
        difficultyNoRadioButton = new JRadioButton();
        difficultyEasyRadioButton = new JRadioButton();
        difficultyMediumRadioButton = new JRadioButton();
        difficultyHardRadioButton = new JRadioButton();
        registerLabel = new JLabel();
        loginButton = new JButton();

        int radioButtonWidth = difficultyNoRadioButton.getPreferredSize().width + 100;
        int radioButtonHeight = difficultyNoRadioButton.getPreferredSize().height;

        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setMaximumSize(new Dimension(550, 700));
        setMinimumSize(new Dimension(550, 700));
        setPreferredSize(new Dimension(550, 700));
        setLayout(null);

        //---- playermanager ----
        add(playerManagerView);
        playerManagerView.setBounds(550,0,550,700);

        //---- title ----
        title.setText("Login");
        title.setFont(new Font("Arial", Font.PLAIN, 50));
        title.setBackground(SwingColors.BackGroundColor);
        title.setForeground(SwingColors.BlueColor);
        add(title);
        title.setBounds(50, 45, 275, 65);

        //---- playerNameLabel ----
        playerNameLabel.setText("Spielername");
        playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        playerNameLabel.setBackground(SwingColors.BackGroundColor);
        playerNameLabel.setForeground(SwingColors.TextColor);
        add(playerNameLabel);
        playerNameLabel.setBounds(55,155, (int) (playerNameLabel.getPreferredSize().getWidth()+10), (int) playerNameLabel.getPreferredSize().getHeight());

        //---- passwordLabel ----
        passwordLabel.setText("Passwort");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setBackground(SwingColors.BackGroundColor);
        passwordLabel.setForeground(SwingColors.TextColor);
        add(passwordLabel);
        passwordLabel.setBounds(new Rectangle(new Point(55, 220), passwordLabel.getPreferredSize()));

        //---- passwordField ----
        passwordField.setBorder(new RoundedBorder(5));
        passwordField.setBackground(SwingColors.BackGroundColor);
        add(passwordField);
        passwordField.setBounds(55, 245, 285, passwordField.getPreferredSize().height);

        //---- usernameField ----
        usernameField.setBorder(new RoundedBorder(5));
        usernameField.setBackground(SwingColors.BackGroundColor);
        add(usernameField);
        usernameField.setBounds(55, 180, 285, usernameField.getPreferredSize().height);

        //---- status ----
        status.setText("");
        status.setFont(new Font("Arial", Font.PLAIN, 16));
        status.setBackground(SwingColors.BackGroundColor);
        status.setForeground(SwingColors.TextColor);
        add(status);
        status.setBounds(55, 285, status.getPreferredSize().width, status.getPreferredSize().height);

        //---- difficultyLabel ----
        difficultyLabel.setText("Spieler intelligent machen?");
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        difficultyLabel.setBackground(SwingColors.BackGroundColor);
        difficultyLabel.setForeground(SwingColors.TextColor);
        add(difficultyLabel);
        difficultyLabel.setBounds(55, 340, difficultyLabel.getPreferredSize().width+10, difficultyLabel.getPreferredSize().height);

        //---- difficultyNoRadioButton ----
        difficultyNoRadioButton.setText("Nein");
        difficultyNoRadioButton.setBackground(SwingColors.BackGroundColor);
        difficultyNoRadioButton.setForeground(SwingColors.TextColor);
        difficultyNoRadioButton.setSelected(true);
        add(difficultyNoRadioButton);
        difficultyNoRadioButton.setBounds(55,375,radioButtonWidth, radioButtonHeight);

        //---- difficultyEasyRadioButton ----
        difficultyEasyRadioButton.setText("Easy");
        difficultyEasyRadioButton.setBackground(SwingColors.BackGroundColor);
        difficultyEasyRadioButton.setForeground(SwingColors.TextColor);
        add(difficultyEasyRadioButton);
        difficultyEasyRadioButton.setBounds(55, 410, radioButtonWidth, radioButtonHeight);

        //---- difficultyMediumRadioButton ----
        difficultyMediumRadioButton.setText("Medium");
        difficultyMediumRadioButton.setBackground(SwingColors.BackGroundColor);
        difficultyMediumRadioButton.setForeground(SwingColors.TextColor);
        add(difficultyMediumRadioButton);
        difficultyMediumRadioButton.setBounds(55, 445, radioButtonWidth, radioButtonHeight);

        //---- difficultyHardRadioButton ----
        difficultyHardRadioButton.setText("Hard");
        difficultyHardRadioButton.setBackground(SwingColors.BackGroundColor);
        difficultyHardRadioButton.setForeground(SwingColors.TextColor);
        add(difficultyHardRadioButton);
        difficultyHardRadioButton.setBounds(55, 480, radioButtonWidth, radioButtonHeight);

        //---- registerLabel ----
        registerLabel.setText("Neuen Account erstellen?");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        registerLabel.setBackground(SwingColors.BackGroundColor);
        registerLabel.setForeground(SwingColors.BlueColor);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenter.showRegisterView();
            }
        });
        add(registerLabel);
        registerLabel.setBounds(55, 580, 200,15);

        //---- loginButton ----
        loginButton.setText("Einloggen");
        loginButton.setBackground(SwingColors.BackGroundColor);
        loginButton.setForeground(SwingColors.TextColor);
        loginButton.addActionListener(e -> {
            AgentDifficulty difficulty = null;

            if (difficultyEasyRadioButton.isSelected())
                difficulty = AgentDifficulty.EASY;
            else if (difficultyMediumRadioButton.isSelected())
                difficulty = AgentDifficulty.MEDIUM;
            else if (difficultyHardRadioButton.isSelected())
                difficulty = AgentDifficulty.HARD;

            presenter.login(usernameField.getText(), String.valueOf(passwordField.getPassword()), difficulty);
        });
        add(loginButton);
        loginButton.setBounds(390, 575, loginButton.getPreferredSize().width+20, 30);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }

        //---- difficultyRadioButtonGroup ----
        ButtonGroup difficultyRadioButtonGroup = new ButtonGroup();
        difficultyRadioButtonGroup.add(difficultyNoRadioButton);
        difficultyRadioButtonGroup.add(difficultyEasyRadioButton);
        difficultyRadioButtonGroup.add(difficultyMediumRadioButton);
        difficultyRadioButtonGroup.add(difficultyHardRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }



    @Override
    public void updateStatusLabelSuccess() {
        status.setText("Sie haben sich erfolgreich eingeloggt.");
        status.setForeground(SwingColors.SuccesColor);
        status.setSize(status.getPreferredSize().width, status.getPreferredSize().height);
    }

    @Override
    public void updateStatusLabelError() {
        status.setText("Fehler bei der Anmeldung");
        status.setForeground(SwingColors.ErrorColor);
        status.setSize(status.getPreferredSize().width, status.getPreferredSize().height);
    }

    @Override
    public void updateStatusLabelErrorEmptyTextField() {
        status.setText("Bitte geben sie ihre Spielerdaten an um sich anzumelden!");
        status.setForeground(SwingColors.ErrorColor);
        status.setSize(status.getPreferredSize().width, status.getPreferredSize().height);
    }

    @Override
    public void updatePlayerManagerView() {
        playerManagerView.updateLabel(usernameField.getText());
        usernameField.setText("");
        passwordField.setText("");
    }


    @Override
    public void setPresenter(ILoginPresenter presenter) {
        this.presenter =  presenter;
    }

    public JLabel getStatus() {
        return status;
    }

    public PlayerManagerView getPlayerManagerView() {
        return playerManagerView;
    }

    public void setUsernameField(String userName) {
        this.usernameField.setText(userName);
    }
}
