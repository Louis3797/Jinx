package org.jinx.game_state;

import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.field.Field;
import org.jinx.player.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Datastructure for saving the game
 */
public class GameState implements Serializable {

    public static final long serialVersionUID = 42L;
    public NumberCardStack deck;
    public LuckyCardStack luckyDeck;
    public Field field;
    public int currentRound;
    public List<Player> player;
    public Player currentPlayer;
    public boolean txt;

    @Override
    public String toString() {
        return "SaveData{" +
                "deck=" + deck +
                ", luckyDeck=" + luckyDeck +
                ", field=" + field +
                ", currentRound=" + currentRound +
                ", player=" + player +
                ", currentPlayer=" + currentPlayer +
                ", txt=" + txt +
                '}';
    }
}
