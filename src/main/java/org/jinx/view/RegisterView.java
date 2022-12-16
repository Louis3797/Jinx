/*
 * Created by JFormDesigner on Wed Dec 14 17:55:49 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.interfaces.IRegisterPresenter;
import org.jinx.swing.RoundedBorder;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IRegisterView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author unknown
 */
public class RegisterView extends JPanel implements IRegisterView {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel title;
    private JLabel playerNameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JLabel status;
    private JLabel loginLabel;
    private JButton registerButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    /**
     * Presenter for the View
     */
    private IRegisterPresenter presenter;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(SwingColors.LightBlueColor);
        g.fillRect(850,0,30,700);
        g.fillRect(0,600,1100,30);
    }

    public RegisterView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        title = new JLabel();
        playerNameLabel = new JLabel();
        passwordLabel = new JLabel();
        usernameField = new JTextField();
        passwordField = new JTextField();
        status = new JLabel();
        loginLabel = new JLabel();
        registerButton = new JButton();

        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setMinimumSize(new Dimension(1100, 700));
        setMaximumSize(new Dimension(1100, 700));
        setPreferredSize(new Dimension(1100, 700));
        setLayout(null);

        //---- title ----
        title.setText("Neuen Spieler registrieren ");
        title.setFont(new Font("Arial", Font.PLAIN, 40));
        title.setBackground(SwingColors.BackGroundColor);
        title.setForeground(SwingColors.BlueColor);
        add(title);
        title.setBounds(new Rectangle(new Point(50, 45), title.getPreferredSize()));

        //---- usernameLabel ----
        playerNameLabel.setText("Spielername");
        playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        playerNameLabel.setBackground(SwingColors.BackGroundColor);
        playerNameLabel.setForeground(SwingColors.TextColor);
        add(playerNameLabel);
        playerNameLabel.setBounds(new Rectangle(new Point(55, 155), playerNameLabel.getPreferredSize()));

        //---- usernameField ----
        usernameField.setBackground(SwingColors.BackGroundColor);
        usernameField.setBorder(new RoundedBorder(5));
        add(usernameField);
        usernameField.setBounds(55, 180, 285, usernameField.getPreferredSize().height);

        //---- passwordLabel ----
        passwordLabel.setText("Passwort");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setBackground(SwingColors.BackGroundColor);
        passwordLabel.setForeground(SwingColors.TextColor);
        add(passwordLabel);
        passwordLabel.setBounds(new Rectangle(new Point(55, 220), passwordLabel.getPreferredSize()));

        //---- passwordField ----
        passwordField.setBackground(SwingColors.BackGroundColor);
        passwordField.setBorder(new RoundedBorder(5));
        add(passwordField);
        passwordField.setBounds(55, 245, 285, passwordField.getPreferredSize().height);

        //---- status ----
        status.setText("");
        status.setFont(new Font("Arial", Font.PLAIN, 16));
        status.setBackground(SwingColors.BackGroundColor);
        status.setForeground(SwingColors.TextColor);
        add(status);
        status.setBounds(55, 285, 85, 15);

        //---- loginLabel ----
        loginLabel.setText("Einloggen?");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        loginLabel.setBackground(SwingColors.BackGroundColor);
        loginLabel.setForeground(SwingColors.BlueColor);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenter.showLoginView();
            }
        });
        add(loginLabel);
        loginLabel.setBounds(55, 355, 110, loginLabel.getPreferredSize().height);

        //---- registerButton ----
        registerButton.setText("Registrieren");
        registerButton.setBackground(SwingColors.BackGroundColor);
        registerButton.setForeground(SwingColors.TextColor);
        registerButton.addActionListener(e -> presenter.register(usernameField.getText(), passwordField.getText()));
        add(registerButton);
        registerButton.setBounds(new Rectangle(new Point(260, 350), registerButton.getPreferredSize()));

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    @Override
    public void updateStatusLabelSuccess() {
        status.setText("Spieler wurde erfolgreich erstellt. Loggen sie sich nun ein.");
        status.setForeground(SwingColors.SuccesColor);
        status.setSize(status.getPreferredSize().width, status.getPreferredSize().height);
    }

    @Override
    public void updateStatusLabelError() {
        status.setText("Fehler bei der Registrierung Spieler existiert schon!");
        status.setForeground(SwingColors.ErrorColor);
        status.setSize(status.getPreferredSize().width, status.getPreferredSize().height);
    }

    @Override
    public void updateStatusLabelErrorEmptyTextField() {
        status.setText("Bitte fühlen sie die Textfelder aus");
        status.setForeground(SwingColors.ErrorColor);
        status.setSize(status.getPreferredSize().width, status.getPreferredSize().height);
    }

    @Override
    public void setPresenter(IRegisterPresenter presenter) {
        this.presenter = presenter;
    }
}
