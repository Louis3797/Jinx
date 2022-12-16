package org.jinx.view;

import org.jinx.presenter.interfaces.IStartPresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IStartView;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class StartView extends JPanel implements IStartView {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel title;
    private JButton startGameButton;
    private JButton highscoreButton;
    private JRadioButton textfileRadioButton;
    private JRadioButton databaseRadioButton;
    private JLabel radioButtonLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    /**
     * Presenter for the View
     */
    private IStartPresenter presenter;


    public StartView() {
        initComponents();
    }

    Image image = Toolkit.getDefaultToolkit().getImage("360_F_355607062_zYMS8jaz4SfoykpWz5oViRVKL32IabTP.jpg");

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        title = new JLabel();
        startGameButton = new JButton();
        highscoreButton = new JButton();
        textfileRadioButton = new JRadioButton();
        databaseRadioButton = new JRadioButton();
        radioButtonLabel = new JLabel();

        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setMinimumSize(new Dimension(1100, 700));
        setMaximumSize(new Dimension(1100, 700));
        setPreferredSize(new Dimension(1100, 700));
        setLayout(null);

        //---- title ----
        title.setText("Jinx");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBackground(Color.white);
        title.setForeground(SwingColors.BlueColor);
        title.setFont(new Font("Arial", Font.BOLD, 80));
        add(title);
        title.setBounds(450, 170, 200, 80);

        //---- startGameButton ----
        startGameButton.setText("Start  Game");
        startGameButton.setForeground(SwingColors.TextColor);
        startGameButton.setBackground(SwingColors.BackGroundColor);
        startGameButton.addActionListener(e -> presenter.startGame());
        add(startGameButton);
        startGameButton.setBounds(480, 405, 140, startGameButton.getPreferredSize().height);

        //---- highscoreButton ----
        highscoreButton.setText("Show Highscores");
        highscoreButton.setBackground(SwingColors.BackGroundColor);
        highscoreButton.setForeground(SwingColors.TextColor);
        highscoreButton.addActionListener(e -> presenter.showHighScoreView());
        add(highscoreButton);
        highscoreButton.setBounds(480, 450, 140, highscoreButton.getPreferredSize().height);

        //---- textfileRadioButton ----
        textfileRadioButton.setText("Textdatei");
        textfileRadioButton.setBackground(SwingColors.BackGroundColor);
        textfileRadioButton.setForeground(SwingColors.TextColor);
        textfileRadioButton.setSelected(true);
        textfileRadioButton.addActionListener(e -> presenter.setTextFileAsDataStorage());
        add(textfileRadioButton);
        textfileRadioButton.setBounds(420, 335, 105, textfileRadioButton.getPreferredSize().height);

        //---- databaseRadioButton ----
        databaseRadioButton.setText("Database");
        databaseRadioButton.setBackground(SwingColors.BackGroundColor);
        databaseRadioButton.setForeground(SwingColors.TextColor);
        databaseRadioButton.addActionListener(e -> presenter.setDBAsDataStorage());
        add(databaseRadioButton);
        databaseRadioButton.setBounds(600, 335, databaseRadioButton.getPreferredSize().width, databaseRadioButton.getPreferredSize().height);

        //---- radioButtonLabel ----
        radioButtonLabel.setText("Wo wollen sie ihre Daten speichern lassen?");
        radioButtonLabel.setForeground(SwingColors.TextColor);
        add(radioButtonLabel);
        radioButtonLabel.setBounds(420, 300, radioButtonLabel.getPreferredSize().width, radioButtonLabel.getPreferredSize().height);

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

        //---- radioButtonGroup ----
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(textfileRadioButton);
        radioButtonGroup.add(databaseRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on


    }

    @Override
    public void setPresenter(IStartPresenter presenter) {
        this.presenter = presenter;
    }
}
