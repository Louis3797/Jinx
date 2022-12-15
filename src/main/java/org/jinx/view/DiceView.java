/*
 * Created by JFormDesigner on Thu Dec 15 13:38:05 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.interfaces.IDicePresenter;
import org.jinx.swing.RoundedBorder;
import org.jinx.view.interfaces.IDiceView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author unknown
 */
public class DiceView extends JPanel implements IDiceView {

    private int currentDicePosition = 6;

    private IDicePresenter presenter;

    public DiceView() {
        initComponents();
        repaint();
    }

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off

        //======== this ========
        setMaximumSize(new Dimension(75, 75));
        setMinimumSize(new Dimension(75, 75));
        setPreferredSize(new Dimension(75, 75));
        setBackground(Color.white);

        setBorder(new RoundedBorder(10));
        setLayout(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenter.useDice();
            }
        });

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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentDicePosition < 1 || currentDicePosition > 6) {
            throw new IllegalArgumentException("DisplayedNumber must be in range from 1 to 6");
        }

        g.setColor(Color.BLACK);

        if (currentDicePosition == 1) {
            g.fillOval(30, 30, 15, 15);
        } else if (currentDicePosition == 2) {
            g.fillOval(10, 10, 15, 15);

            g.fillOval(50, 50, 15, 15);
        } else if (currentDicePosition == 3) {
            g.fillOval(10, 10, 15, 15);
            g.fillOval(30, 30, 15, 15);
            g.fillOval(50, 50, 15, 15);
        } else if (currentDicePosition == 4) {
            g.fillOval(10, 10, 15, 15);
            g.fillOval(50, 10, 15, 15);
            g.fillOval(50, 50, 15, 15);
            g.fillOval(10, 50, 15, 15);
        } else if (currentDicePosition == 5) {
            g.fillOval(10, 10, 15, 15);
            g.fillOval(50, 10, 15, 15);
            g.fillOval(30, 30, 15, 15);
            g.fillOval(50, 50, 15, 15);
            g.fillOval(10, 50, 15, 15);
        } else {
            g.fillOval(10, 10, 15, 15);
            g.fillOval(50, 10, 15, 15);
            g.fillOval(10, 30, 15, 15);
            g.fillOval(50, 30, 15, 15);
            g.fillOval(50, 50, 15, 15);
            g.fillOval(10, 50, 15, 15);
        }


    }

    @Override
    public void updateDice(int dicePosition) {
        currentDicePosition = dicePosition;
        repaint();
    }

    @Override
    public void setPresenter(IDicePresenter presenter) {
        this.presenter = presenter;
    }
}

