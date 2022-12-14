/*
 * Created by JFormDesigner on Wed Dec 14 12:41:27 CET 2022
 */

package org.jinx.view;

import java.awt.event.*;
import javax.swing.border.*;
import org.jinx.presenter.IPresenter;
import org.jinx.presenter.IStartPresenter;
import org.jinx.swing.RoundedBorder;

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
        setBackground(new Color(0xf1f1f1));
        setMinimumSize(new Dimension(1100, 700));
        setMaximumSize(new Dimension(1100, 700));
        setPreferredSize(new Dimension(1100, 700));
        setLayout(null);

        //---- title ----
        title.setText("Jinx");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBackground(Color.white);
        title.setForeground(new Color(0x4b56d2));
        title.setFont(new Font("Arial", Font.BOLD, 80));
        add(title);
        title.setBounds(450, 170, 200, 80);

        //---- startGameButton ----
        startGameButton.setText("Start  Game");
        startGameButton.setForeground(Color.black);
        startGameButton.setBackground(new Color(0x4b56d2));
        startGameButton.setBorder(new RoundedBorder(5));
        add(startGameButton);
        startGameButton.setBounds(480, 405, 140, startGameButton.getPreferredSize().height);

        //---- highscoreButton ----
        highscoreButton.setText("Show Highscores");
        highscoreButton.setBackground(new Color(0x4b56d2));
        highscoreButton.setForeground(Color.black);
        highscoreButton.setBorder(new RoundedBorder(5));
        add(highscoreButton);
        highscoreButton.setBounds(480, 450, 140, highscoreButton.getPreferredSize().height);

        //---- textfileRadioButton ----
        textfileRadioButton.setText("Textdatei");
        textfileRadioButton.setBackground(new Color(0xf1f1f1));
        textfileRadioButton.setForeground(Color.black);
        textfileRadioButton.setSelected(true);
        add(textfileRadioButton);
        textfileRadioButton.setBounds(420, 335, 105, textfileRadioButton.getPreferredSize().height);

        //---- databaseRadioButton ----
        databaseRadioButton.setText("Database");
        databaseRadioButton.setBackground(new Color(0xf1f1f1));
        databaseRadioButton.setForeground(Color.black);
        add(databaseRadioButton);
        databaseRadioButton.setBounds(600, 335, databaseRadioButton.getPreferredSize().width, databaseRadioButton.getPreferredSize().height);

        //---- radioButtonLabel ----
        radioButtonLabel.setText("Wo wollen sie ihre Daten speichern lassen?");
        radioButtonLabel.setForeground(Color.black);
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
        var radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(textfileRadioButton);
        radioButtonGroup.add(databaseRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    @Override
    public void setPresenter(IPresenter<IStartPresenter, IStartView> presenter) {
        this.presenter = (IStartPresenter) presenter;
    }
}
