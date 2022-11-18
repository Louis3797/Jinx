package org.jinx.field;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.utils.ConsoleColor;

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
    private final int FIELD_SIZE = 16;

    /**
     * Stores the Cards on the field
     */
    NumberCard[] field;

    /**
     * Standard Constructor
     */
    private Field() {
        field = new NumberCard[FIELD_SIZE];
    }

    /**
     * Call this method every time you start a round so that the field is set
     */
    public void setField(NumberCardStack deck) {

        for (int i = 0; i < FIELD_SIZE; i++) {
            field[i] = deck.pop();
        }
    }

    /**
     * Removes the selected card of the player from the field
     *
     * @param card Selected card
     */
    public void removeChosenCard(NumberCard card) {

        for (int i = 0; i < FIELD_SIZE; i++) {
            if (field[i] != null && field[i].equals(card)) {
                field[i] = null;
                break;
            }
        }
    }

    /**
     * Counts how much the card color occurs in the specified list
     *
     * @param cards List of NumberCards
     * @param color Specified card color
     * @return Returns how often the color occurs in the list
     */
    public int countCardColor(CardColor color) {
        int sum = 0;
        for (NumberCard card : field) {
            if (card != null && card.getColor().equals(color)) ++sum;
        }
        return sum;
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

            for (int j = 0; j < 4; j++) {
                System.out.print(
                        (field[i * 4 + j] != null ? ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") : "") +
                                "----------\t" + ConsoleColor.RESET);
            }
            // for new line
            System.out.println();

            for (int j = 0; j < 4; j++) {
                System.out.print(
                        (field[i * 4 + j] != null ? ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") : "") +
                                "|        |\t" + ConsoleColor.RESET);
            }

            // for new line
            System.out.println();

            for (int j = 0; j < 4; j++) {
                System.out.print(field[i * 4 + j] == null ? "|        |\t" : ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") + "| " + field[i * 4 + j].getName() + " ".repeat(6) + "|\t" + ConsoleColor.RESET);
            }

            System.out.println();

            for (int j = 0; j < 4; j++) {
                System.out.print(
                        (field[i * 4 + j] != null ? ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") : "") +
                                "|        |\t" + ConsoleColor.RESET);
            }
            // for new line
            System.out.println();


            for (int j = 0; j < 4; j++) {
                System.out.print(field[i * 4 + j] == null ? "|        |\t" : ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") + "| " + field[i * 4 + j].getColor() + (field[i * 4 + j].getColor().name().length() < 6 ? (" ".repeat(6 - field[i * 4 + j].getColor().name().length())) : "") + " |\t" + ConsoleColor.RESET);
            }

            System.out.println();

            for (int j = 0; j < 4; j++) {

                System.out.print(
                        (field[i * 4 + j] != null ? ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") : "") +
                                "|        |\t" + ConsoleColor.RESET);
            }
            // for new line
            System.out.println();


            for (int j = 0; j < 4; j++) {

                System.out.print(
                        (field[i * 4 + j] != null ? ConsoleColor.valueOf(field[i * 4 + j].getColor().name() + "_BRIGHT") : "") +
                                "----------\t" + ConsoleColor.RESET);
            }
            // for new line
            System.out.println();

        }
    }

    public static Field getFieldInstance() {
        return fieldInstance;
    }

    /* ---------- Getter and Setter Methods ---------- */

    public int getFieldSize() {
        return FIELD_SIZE;
    }

    public NumberCard[] getField() {
        return field;
    }
}
