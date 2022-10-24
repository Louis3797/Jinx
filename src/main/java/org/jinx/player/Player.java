package org.jinx.player;

import org.jinx.card.LuckyCard;
import org.jinx.card.NumberCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    /**
     * Name of the player
     */
    private final String name;

    /**
     * Stores if the Player used the redo funtion in game to get the old dice result back.
     * If true, then player Highscore should not be listed after game
     */
    private boolean usedRedo = false;

    /**
     * Stores the cards of the player in game
     */
    private final List<NumberCard> cards;

    private final List<LuckyCard> luckyCards;

    /**
     * Standard Constructor for the Player
     *
     * @param name Name of the player
     */
    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.luckyCards = new ArrayList<>();
    }

    /**
     * Prints hand of player
     */
    public void printHand() {

        System.out.print("----------\t".repeat(cards.size()) + "\n");

        System.out.println("|        |\t".repeat(cards.size()));

        // print card number
        for (NumberCard card : cards) {
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

    /**
     * Prints LuckyCards of the Player
     */
    public void printLuckyHand() {
        for (LuckyCard card : getLuckyCards()) {
            System.out.println(card.getName());
        }
    }

    /* ---------- Getter and Setter Methods ---------- */
    public String getName() {
        return name;
    }

    public boolean isUsedRedo() {
        return usedRedo;
    }

    public void setUsedRedo(boolean usedRedo) {
        this.usedRedo = usedRedo;
    }

    public List<NumberCard> getCards() {
        return cards;
    }

    public List<LuckyCard> getLuckyCards() {
        return luckyCards;
    }
}
