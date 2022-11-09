package org.jinx.field;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;

import java.util.ArrayList;
import java.util.List;

public class Field {

    /**
     * Instance for singleton
     */
    private static final Field fieldInstance = new Field();

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
    private Field() {
        field = new NumberCard[FIELDSIZE];
    }

    public static Field getFieldInstance() {
        return fieldInstance;
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

        //top of the cards
        NumberCard.printFormatedNumberCards(availableCards);
    }

    /**
     * Prints the field on the Terminal
     */
    public void printField() {
        for (int i = 0; i < 4; i++) {

            System.out.print("----------\t".repeat(4) + "\n");

            System.out.println("|        |\t".repeat(4));

            for (int j = 0; j < 4; j++) {
                System.out.print(field[i * 4 + j] == null ? "|        |\t" : "| " + field[i * 4 + j].getName() + " ".repeat(6) + "|\t");
            }

            System.out.println();
            System.out.println("|        |\t".repeat(4));

            for (int j = 0; j < 4; j++) {
                System.out.print(field[i * 4 + j] == null ? "|        |\t" : "| " + field[i * 4 + j].getColor() + (field[i * 4 +j].getColor().name().length() < 6 ? (" ".repeat(6 - field[i * 4 +j].getColor().name().length())) : "") + " |\t");
            }

            System.out.println();
            System.out.println("|        |\t".repeat(4));
            System.out.print("----------\t".repeat(4) + "\n");

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
