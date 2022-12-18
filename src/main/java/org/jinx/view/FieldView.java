/*
 * Created by JFormDesigner on Thu Dec 15 12:19:26 CET 2022
 */

package org.jinx.view;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.dice.IDice;
import org.jinx.presenter.interfaces.IDicePresenter;
import org.jinx.presenter.interfaces.IFieldPresenter;
import org.jinx.presenter.interfaces.IGamehistoryPresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IFieldView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author unknown
 */
public class FieldView extends JPanel implements IFieldView {

    private IFieldPresenter presenter;

    private List<NumberCard> cards;
    private List<NumberCardView> cardViews;

    public FieldView() {
        this.cards = new ArrayList<>();
        this.cardViews = new ArrayList<>();
        initComponents();
    }

    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off

        //======== this ========
        setMaximumSize(new Dimension(255, 355));
        setMinimumSize(new Dimension(255, 355));
        setPreferredSize(new Dimension(255, 355));
        setBackground(SwingColors.BackGroundColor);
        setLayout(new GridLayout(4, 4, 5, 5));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    @Override
    public void setPresenter(IFieldPresenter presenter) {
        this.presenter = presenter;
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
                    int dice = ((IDice) presenter.getModel()).getCurrentDicePosition();
                    if(Integer.parseInt(cardView.getNumber()) == dice){
                        e.getComponent().setVisible(false);
                    }
                }
            });

            // add to view
            this.add(cardView);
        }

    }

}
