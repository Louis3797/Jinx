package org.jinx.savestate;

import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.field.Field;

import java.io.Serializable;

public class SaveData implements Serializable {

    public NumberCardStack deck;
    public LuckyCardStack luckyDeck;
    public Field field;
    public int currentRound;

}
