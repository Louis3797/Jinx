/*
 * Created by JFormDesigner on Thu Dec 15 12:17:32 CET 2022
 */

package org.jinx.view;

import org.jinx.cardstack.NumberCardStack;
import org.jinx.dice.Dice;
import org.jinx.game.PlayerManager;
import org.jinx.presenter.DicePresenter;
import org.jinx.presenter.FieldPresenter;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class GameView extends JPanel {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private FieldView fieldView;
    private FieldPresenter fieldPresenter;
    private JLabel playerNameLabel4;
    private JLabel playerNameLabel2;
    private JLabel playerNameLabel3;

    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private DiceView diceView;

    private Dice dice;
    public GameView() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dice = new Dice();
        fieldView = new FieldView();
        fieldPresenter = new FieldPresenter(fieldView, dice);

        playerNameLabel4 = new JLabel();
        playerNameLabel2 = new JLabel();
        playerNameLabel3 = new JLabel();

        PlayerHandView playerHandView = new PlayerHandView();

        diceView = new DiceView();
        DicePresenter dicePresenter = new DicePresenter(diceView, dice);

        //======== this ========
        setMaximumSize(new Dimension(1100, 700));
        setMinimumSize(new Dimension(1100, 700));
        setPreferredSize(new Dimension(1100, 700));
        setLayout(null);

        //---- field -----
        add(fieldView);
        fieldView.setBounds(new Rectangle(new Point(422, 100), fieldView.getPreferredSize()));
        fieldView.fillField(new NumberCardStack());
        fieldView.updateField();

        //---- playerNameLabel4 ----
        playerNameLabel4.setText("Bob");
        playerNameLabel4.setFont(new Font("Arial", Font.PLAIN, 18));
        playerNameLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerNameLabel4);
        playerNameLabel4.setBounds(new Rectangle(new Point(55, 300), playerNameLabel4.getPreferredSize()));

        //---- playerNameLabel2 ----
        playerNameLabel2.setText("Bob");
        playerNameLabel2.setFont(new Font("Arial", Font.PLAIN, 18));
        playerNameLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerNameLabel2);
        playerNameLabel2.setBounds(1010, 310, 32, 22);

        //---- playerNameLabel3 ----
        playerNameLabel3.setText("Bob");
        playerNameLabel3.setFont(new Font("Arial", Font.PLAIN, 18));
        playerNameLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerNameLabel3);
        playerNameLabel3.setBounds(420, 35, 255, 22);

        //---- diceView -----
        add(diceView);
        diceView.setBounds(725, 245, 75, 75);

        add(playerHandView);
        playerHandView.setBounds(250, 490, playerHandView.getPreferredSize().width, playerHandView.getPreferredSize().height);

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

}
