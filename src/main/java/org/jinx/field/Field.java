package org.jinx.field;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;

import java.util.ArrayList;
import java.util.List;

public class Field {

    /**
     * Size of the Field
     */
    private final int FIELDSIZE = 16;

    /**
     * Stores the Cards on the field
     */
    NumberCard[] field;

    /**
     * Standard Constructor
     */
    public Field() {
        field = new NumberCard[FIELDSIZE];
    }

    /**
     * Call this method every time you start a round so that the field is set
     */
    public void setField(NumberCardStack deck) {

        for (int i = 0; i < FIELDSIZE; i++) {
            field[i] = deck.pop();
        }
    }

    /**
     * Removes the selected card of the player from the field
     *
     * @param card Selected card
     */
    public void removeChosenCard(NumberCard card) {

        for (int i = 0; i < FIELDSIZE; i++) {
            if (field[i] != null && field[i].equals(card)) {
                field[i] = null;
                break;
            }
        }
    }

    /**
     * Lists all available cards to choose from
     *
     * @param diceNumber number the player rolled
     * @return returns list of available cards
     */
    public List<NumberCard> getAvailableNumberCards(int diceNumber) {
        List<NumberCard> cards = new ArrayList<>();

        for (NumberCard c : field) {
            if (c != null && c.getName().equals(Integer.toString(diceNumber))) {
                cards.add(c);
            }
        }

        return cards;
    }

    /**
     * prints all cards to choose from
     *
     * @param availableCards List of available Cards
     */
    public void printAvailableCards(List<NumberCard> availableCards) {
        System.out.println("AVAILABLE CARDS");
        for (NumberCard card : availableCards) {
            System.out.print(card.toString() + " ");
        }
    }

    /**
     * Prints the field on the Terminal
     */
    public void printField() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(field[i * 4 + j] == null ? "\t\t\t" : field[i * 4 + j].toString() + " \t");
            }

            System.out.println();
        }
    }

    /* ---------- Getter and Setter Methods ---------- */

    public int getFieldSize() {
        return FIELDSIZE;
    }

    public NumberCard[] getField() {
        return field;
    }
}