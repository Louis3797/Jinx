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
     * @param cards List of Numbercards to print
     */
    public static void printFormatedNumberCards(List<NumberCard> cards) {
        System.out.print("----------\t".repeat(cards.size()) + "\n");

        System.out.println("|        |\t".repeat(cards.size()));

        // print card number
        for (NumberCard card :cards) {
            System.out.print("| " + card.getName() + " ".repeat(6) + "|\t");
        }

        System.out.println();
        System.out.println("|        |\t".repeat(cards.size()));

        for (NumberCard card : cards) {
            System.out.print("| " + card.getColor() + (card.getColor().name().length() < 6 ? (" ".repeat(6 - card.getColor().name().length())) : "") + " |\t");
        }
        System.out.println();
        System.out.println("|        |\t".repeat(cards.size()));
        System.out.print("----------\t".repeat(cards.size()) + "\n");
    }

    public CardColor getColor() {
        return this.color;
    }
}
