package org.jinx.card;

import java.util.List;

public abstract class LuckyCard extends Card {

    public LuckyCard(LuckyCardNames name) {
        super(name.name());
    }

    public abstract int effect() throws IllegalAccessException;

    public static void printFormatedLuckyCards(List<LuckyCard> cards) {
        System.out.print("-------------------\t".repeat(cards.size()) + "\n");

        System.out.println("|                 |\t".repeat(cards.size()));

        // print card number
        for (LuckyCard card : cards) {
            System.out.print("| " + card.getName() + " ".repeat(16 - card.getName().length()) + "|\t");
        }

        System.out.println();
        System.out.println("|                 |\t".repeat(cards.size()));

        for (LuckyCard card : cards) {
            System.out.print("| " + card.getName() + " ".repeat(16 - card.getName().length()) + "|\t");
        }
        System.out.println();
        System.out.println("|                 |\t".repeat(cards.size()));
        System.out.print("-------------------\t".repeat(cards.size()) + "\n");
    }

}
