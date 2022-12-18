package org.jinx.player;

import org.jinx.cardhand.LuckyCardHand;
import org.jinx.cardhand.NumberCardHand;
import org.jinx.history.PlayerHistory;
import org.jinx.model.IModel;

import java.io.Serializable;
import java.util.List;

public interface IPlayer extends Serializable, IModel {
    long serialVersionUID = 42L;

    int getPoints();

    void printHistory();

    boolean isHuman();

    /* ---------- Getter and Setter Methods ---------- */
    String getName();

    boolean isUsedCheats();

    void setUsedCheats(boolean usedCheats);

    NumberCardHand getNumberCardHand();

    LuckyCardHand getLuckyCardHand();

    List<PlayerHistory> getMatchHistories();

    void setMatchHistories(List<PlayerHistory> matchHistories);
}
