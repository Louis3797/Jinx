package org.jinx.card;

import org.jinx.utils.ConsoleColor;

import java.util.List;

public class NumberCard extends Card {

    private final CardColor color;

    public NumberCard(String name, CardColor color) {
        super(name);
        this.color = color;
    }

    /**
     * Helper Method to print a card array formatted
     *
     * @param cards List of Numbercards to print
     */
    public static void printFormatedNumberCards(List<NumberCard> cards) {

        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "----------\t" + ConsoleColor.RESET);
        }
        System.out.println();

        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "|        |\t" + ConsoleColor.RESET);
        }
        System.out.println();

        // print card number
        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "| " + card.getName() + " ".repeat(6) + "|\t" + ConsoleColor.RESET);
        }
        System.out.println();

        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "|        |\t" + ConsoleColor.RESET);
        }
        System.out.println();


        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "| " + card.getColor() + (card.getColor().name().length() < 6 ? (" ".repeat(6 - card.getColor().name().length())) : "") + " |\t" + ConsoleColor.RESET);
        }
        System.out.println();

        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "|        |\t" + ConsoleColor.RESET);
        }
        System.out.println();

        for (NumberCard card : cards) {
            System.out.print(ConsoleColor.valueOf(card.getColor() + "_BRIGHT") + "----------\t" + ConsoleColor.RESET);
        }
        System.out.println();
    }

    public CardColor getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "NumberCard{" +
                "name=" + getName() +
                ", color=" + color +
                '}';
    }
}
