package org.jinx.player;

import org.jinx.card.LuckyCard;
import org.jinx.card.LuckyCardNames;
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
        NumberCard.printFormatedNumberCards(this.cards);
    }

    /**
     * Prints LuckyCards of the Player
     */
    public void printLuckyHand() {
        LuckyCard.printFormatedLuckyCards(luckyCards);
    }

    /**
     * Method checks if the player has a lucky card with the same given name in his hand
     *
     * @param cardName Given Lucky card name as enum
     * @return Returns true if he holds a Lucky card with the given name in his hand
     */
    public boolean hasLuckyCard(LuckyCardNames cardName) {
        for (LuckyCard card : luckyCards) {
            if (card.getName().equals(cardName.name())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method counts how much Lucky cards the player holds in his hand of the given name
     *
     * @param cardName Given Lucky card name as enum
     * @return Returns the amount of Lucky cards with the same name as the given one
     */
    public int countLuckyCards(LuckyCardNames cardName) {
        int total = 0;
        for (LuckyCard card : luckyCards) {
            if (card.getName().equals(cardName.name())) {
                total++;
            }
        }

        return total;
    }

    /**
     * Method to check if the current Player is a bot or a human
     * <p>
     * This method is overwritten in the Autonomous Player class
     *
     * @return Returns true everytime
     */
    public boolean isHuman() {
        return true;
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
