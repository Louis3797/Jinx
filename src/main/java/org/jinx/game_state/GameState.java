package org.jinx.game_state;

import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.field.Field;
import org.jinx.history.FileHistoryManager;
import org.jinx.history.IHistoryManager;
import org.jinx.login.FileLoginManager;
import org.jinx.login.ILoginManager;
import org.jinx.model.IModel;
import org.jinx.player.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Datastructure for saving the game
 */
public class GameState implements Serializable, IModel {

    public static final long serialVersionUID = 42L;
    public NumberCardStack deck;
    public LuckyCardStack luckyDeck;
    public Field field;
    public int currentRound;
    public List<Player> player;
    public Player currentPlayer;
    public boolean txt;
    private ILoginManager loginManager;
    private IHistoryManager historyManager;

    public GameState(){
        loginManager = new FileLoginManager();
        historyManager = new FileHistoryManager();
    }


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

    public IHistoryManager getHistoryManager() {
        return historyManager;
    }

    public void setHistoryManager(IHistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public ILoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(ILoginManager loginManager) {
        this.loginManager = loginManager;
    }
}
