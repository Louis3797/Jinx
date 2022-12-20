package org.jinx.history;

import org.jinx.model.IModel;
import org.jinx.player.Player;

import java.io.Serializable;
import java.util.List;

public interface IHistoryManager extends IModel, Serializable {

    /**
     * Safes player match History in data store
     */
    void safeHistory();

    /**
     * Loads Player Histories from data storage
     *
     * @param player Given Player
     * @return Returns a List of all PlayerHistories Objects
     */
    List<PlayerHistory> getHistory(Player player);
}
