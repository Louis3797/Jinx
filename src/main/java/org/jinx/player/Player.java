package org.jinx.player;

import org.jinx.card.NumberCard;
import org.jinx.cardhand.LuckyCardHand;
import org.jinx.cardhand.NumberCardHand;
import org.jinx.history.PlayerHistory;
import org.jinx.wrapper.SafeScanner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.jinx.utils.ConsoleColor.RESET;
import static org.jinx.utils.ConsoleColor.WHITE_BOLD_BRIGHT;

public class Player implements IPlayer {

    /**
     * Name of the player
     */
    private final String name;

    /**
     * Stores if the Player used cheats in the game
     */
    private boolean usedCheats = false;

    /**
     * Stores the cards of the player in game
     */
    private final NumberCardHand numberCardHand;

    private final LuckyCardHand luckyCardHand;

    private List<PlayerHistory> matchHistories;

    /**
     * Standard Constructor for the Player
     *
     * @param name Name of the player
     */
    public Player(String name) {
        this.name = name;
        this.numberCardHand = new NumberCardHand();
        this.luckyCardHand = new LuckyCardHand();
        this.matchHistories = new ArrayList<>();
    }

    /**
     * Calculates the points of every umber card in the hand of the player and returns the sum
     *
     * @return Returns the sum off all number card points in our hand
     */
    @Override
    public int getPoints() {
        int sum = 0;
        for (NumberCard card : numberCardHand) {
            if (card != null)
                sum += Integer.parseInt(card.getName());
        }

        return sum;
    }

    @Override
    public void printHistory() {
        SafeScanner safeScanner = new SafeScanner();

        List<PlayerHistory> history = getMatchHistories();

        System.out.println("Liste nach Punkten geordnet ausgeben?");
        // sorts list by points in desc order
        if (safeScanner.nextYesNoAnswer()) {
            history.sort(Comparator.comparingInt(PlayerHistory::cardSum));
        }

        // prints history of player
        for (PlayerHistory h : history) {
            System.out.println(WHITE_BOLD_BRIGHT + "" + h.toString() + RESET);
        }
    }

    /**
     * Method to check if the current Player is a bot or a human
     * <p>
     * This method is overwritten in the Autonomous Player class
     *
     * @return Returns true everytime
     */

    @Override
    public boolean isHuman() {
        return true;
    }

    /* ---------- Getter and Setter Methods ---------- */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isUsedCheats() {
        return usedCheats;
    }

    @Override
    public void setUsedCheats(boolean usedCheats) {
        this.usedCheats = usedCheats;
    }

    @Override
    public NumberCardHand getNumberCardHand() {
        return numberCardHand;
    }

    @Override
    public LuckyCardHand getLuckyCardHand() {
        return luckyCardHand;
    }

    @Override
    public List<PlayerHistory> getMatchHistories() {
        return matchHistories;
    }

    @Override
    public void setMatchHistories(List<PlayerHistory> matchHistories) {
        this.matchHistories = matchHistories;
    }
}
