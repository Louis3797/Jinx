package org.jinx.cardstack;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;

import java.util.Collections;
import java.util.Stack;

public class NumberCardStack extends Stack<NumberCard> {

    public NumberCardStack() {
        generateDeck();
    }

    /**
     * Generates the Deck
     */
    private void generateDeck() {

        for (CardColor color : CardColor.values()) {
            for (int i = 1; i < 7; i++) {
                this.add(new NumberCard(Integer.toString(i), color));
            }
        }

        Collections.shuffle(this);
    }
}