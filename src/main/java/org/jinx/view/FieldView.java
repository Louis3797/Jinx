/*
 * Created by JFormDesigner on Thu Dec 15 12:19:26 CET 2022
 */

package org.jinx.view;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.swing.NumberCardView;
import org.jinx.swing.SwingColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author unknown
 */
public class FieldView extends JPanel {

    private List<NumberCard> cards;
    private List<NumberCardView> cardViews;

    public FieldView() {

        this.cards = new ArrayList<>();
        this.cardViews = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off

        //======== this ========
        setMaximumSize(new Dimension(255, 355));
        setMinimumSize(new Dimension(255, 355));
        setPreferredSize(new Dimension(255, 355));
        setBackground(SwingColors.BackGroundColor);
        setLayout(new GridLayout(4, 4, 5, 5));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    void fillField(NumberCardStack cards) {
        for (int i = 0; i < 16; i++) {
            this.cards.add(cards.pop());
            NumberCard card = this.cards.get(i);
            this.cardViews.add(new NumberCardView(card.getName(), card.getColor()));
        }
    }

    void updateField() {

        for (NumberCardView cardView : cardViews) {
            // add mouse listener
            cardView.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    e.getComponent().setVisible(false);
                }
            });

            // add to view
            this.add(cardView);
        }

    }
}
