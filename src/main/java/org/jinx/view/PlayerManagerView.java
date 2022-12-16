/*
 * Created by JFormDesigner on Fri Dec 16 13:34:51 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.GamehistoryPresenter;
import org.jinx.presenter.interfaces.IPlayerManagerPresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IPlayerManagerView;

import javax.swing.*;
import java.awt.*;


/**
 * @author unknown
 */
public class PlayerManagerView extends JPanel implements IPlayerManagerView {


    private JLabel playername1;
    private JLabel playername2;
    private JLabel playername3;
    private JLabel playername4;
    private JButton historyButton;
    private JButton historyButton2;
    private JButton historyButton3;
    private JButton historyButton4;
    private JButton nextButton;
    private JButton shuffleButton;

    private GamehistoryView gamehistoryView;
    private GamehistoryPresenter gamehistoryPresenter;
    private IPlayerManagerPresenter presenter;

    private JLabel[] labels;
    private JButton[] buttons;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0, 0, 0, 700);
    }

    public PlayerManagerView() {
        initComponents();
    }

    @Override
    public void initComponents() {

        gamehistoryView = new GamehistoryView();
        gamehistoryPresenter = new GamehistoryPresenter(gamehistoryView, MainView.gameState.getHistoryManager());
        MainView.mainPanel.add(gamehistoryView, Views.History.name());

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        playername1 = new JLabel();
        playername2 = new JLabel();
        playername3 = new JLabel();
        playername4 = new JLabel();
        historyButton = new JButton();
        historyButton2 = new JButton();
        historyButton3 = new JButton();
        historyButton4 = new JButton();
        nextButton = new JButton();
        shuffleButton = new JButton();

        labels = new JLabel[]{playername1, playername2, playername3, playername4};
        buttons = new JButton[]{historyButton, historyButton2, historyButton3, historyButton4};

        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setPreferredSize(new Dimension(550, 700));
        setMaximumSize(new Dimension(550, 700));
        setMinimumSize(new Dimension(550, 700));
        setLayout(null);

        //---- Playername1 ----
        playername1.setText("");
        playername1.setBackground(SwingColors.BackGroundColor);
        playername1.setForeground(SwingColors.BlueColor);
        playername1.setFont(new Font("Arial", Font.PLAIN, 18));

        add(playername1);
        playername1.setBounds(50, 110, 150, 60);

        //---- Playername2 ----
        playername2.setText("");
        playername2.setFont(new Font("Arial", Font.PLAIN, 18));
        playername2.setBackground(SwingColors.BackGroundColor);
        playername2.setForeground(SwingColors.BlueColor);
        add(playername2);
        playername2.setBounds(50, 190, 150, 55);

        //---- Playername3 ----
        playername3.setText("");
        playername3.setFont(new Font("Arial", Font.PLAIN, 18));
        playername3.setBackground(SwingColors.BackGroundColor);
        playername3.setForeground(SwingColors.BlueColor);
        add(playername3);
        playername3.setBounds(50, 270, 150, 60);

        //---- Playername4 ----
        playername4.setText("");
        playername4.setFont(new Font("Arial", Font.PLAIN, 18));
        playername4.setBackground(SwingColors.BackGroundColor);
        playername4.setForeground(SwingColors.BlueColor);
        add(playername4);
        playername4.setBounds(50, 350, 150, 60);

        //---- historyButton ----
        historyButton.setVisible(false);
        historyButton.setText("Spielhistorie anzeigen ");
        historyButton.setFont(new Font("Arial", Font.BOLD, 12));
        historyButton.setBackground(SwingColors.BackGroundColor);
        historyButton.setForeground(SwingColors.TextColor);
        historyButton.addActionListener(e -> {
            presenter.updatePlayerLabel(playername1.getText());
            presenter.showHistoryView();
        });
        add(historyButton);
        historyButton.setBounds(280, 120, 200, 40);

        //---- historyButton2 ----
        historyButton2.setVisible(false);
        historyButton2.setText("Spielhistorie anzeigen ");
        historyButton2.setFont(new Font("Arial", Font.BOLD, 12));
        historyButton2.setBackground(SwingColors.BackGroundColor);
        historyButton2.setForeground(SwingColors.TextColor);
        historyButton2.addActionListener(e -> {
            presenter.updatePlayerLabel(playername2.getText());
            presenter.showHistoryView();
        });
        add(historyButton2);
        historyButton2.setBounds(280, 200, 200, 40);

        //---- historyButton3 ----
        historyButton3.setVisible(false);
        historyButton3.setText("Spielhistorie anzeigen ");
        historyButton3.setFont(new Font("Arial", Font.BOLD, 12));
        historyButton3.setBackground(SwingColors.BackGroundColor);
        historyButton3.setForeground(SwingColors.TextColor);
        historyButton3.addActionListener(e -> {
            presenter.updatePlayerLabel(playername3.getText());
            presenter.showHistoryView();
        });
        add(historyButton3);
        historyButton3.setBounds(280, 280, 200, 40);

        //---- historyButton4 ----
        historyButton4.setVisible(false);
        historyButton4.setText("Spielhistorie anzeigen ");
        historyButton4.setFont(new Font("Arial", Font.BOLD, 12));
        historyButton4.setBackground(SwingColors.BackGroundColor);
        historyButton4.setForeground(SwingColors.TextColor);
        historyButton4.addActionListener(e -> {
            presenter.updatePlayerLabel(playername4.getText());
            presenter.showHistoryView();
        });
        add(historyButton4);
        historyButton4.setBounds(280, 360, 200, 40);

        //---- nextButton ----
        nextButton.setText("Weiter");
        nextButton.setFont(new Font("Arial", Font.BOLD, 12));
        nextButton.setBackground(SwingColors.BackGroundColor);
        nextButton.setForeground(SwingColors.TextColor);
        nextButton.addActionListener(e -> presenter.showGameView());
        add(nextButton);
        nextButton.setBounds(380, 575, nextButton.getPreferredSize().width, 30);
        //nextButton.setBounds(380, 640, 105, 40);

        //---- shuffleButton ----
        shuffleButton.setText("Shuffle Spieler ");
        shuffleButton.setFont(new Font("Arial", Font.BOLD, 12));
        shuffleButton.setBackground(SwingColors.BackGroundColor);
        shuffleButton.setForeground(SwingColors.TextColor);
        add(shuffleButton);
        shuffleButton.setBounds(35, 575, 120, 30);
        // shuffleButton.setBounds(35, 640, 105, 40);

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

    }

    @Override
    public void updateHistoryView(String username) {
        gamehistoryPresenter.updatePlayerLabel(username);
    }

    @Override
    public void updateLabel(String playername) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].getText().equals("")) {
                labels[i].setText(playername);
                buttons[i].setVisible(true);
                break;
            }
        }
    }

    @Override
    public void setPresenter(IPlayerManagerPresenter presenter) {
        this.presenter = presenter;
    }

}
