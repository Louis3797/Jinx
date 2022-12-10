package org.jinx.history;

import org.jinx.player.Player;

import java.util.List;

public interface IHistoryManager {

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
