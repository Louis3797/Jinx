package org.jinx.savestate;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.field.Field;
import org.jinx.player.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaveData implements Serializable {
    public static final long serialVersionUID = 42L;
    public NumberCardStack deck;
    public LuckyCardStack luckyDeck;
    public Field field;
    public int currentRound;
    public Map<Player,List<NumberCard>> map;
    public Player currentPlayer;

}
