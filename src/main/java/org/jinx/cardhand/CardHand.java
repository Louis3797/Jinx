package org.jinx.cardhand;

import org.jinx.card.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract Superclass for NumberCardHand and LuckyCardHand
 *
 * @param <T> Generic Type for Arraylist that extends from Cards
 */
public abstract class CardHand<T extends Card> extends ArrayList<T> implements Serializable {

    public static final long serialVersionUID = 42L;
    /**
     * Abstract print Method
     */
    public abstract void print();

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }
}
