package org.jinx.savestate;

import org.jinx.cardhand.NumberCardHand;
import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.field.Field;
import org.jinx.player.Player;

import java.io.Serializable;
import java.util.Map;

/**
 * Datastructure for saving the game
 */
public class SaveData implements Serializable {

    public static final long serialVersionUID = 42L;
    public NumberCardStack deck;
    public LuckyCardStack luckyDeck;
    public Field field;
    public int currentRound;
    public Map<Player, NumberCardHand> map;
    public Player currentPlayer;

}
