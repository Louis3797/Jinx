package org.jinx.history;

import org.jinx.player.Player;

import java.util.List;

public interface IHistoryManager {

    /**
     * Safes player match History in data store
     */
    void safeHistory();

    List<PlayerHistory> getHistory(Player player);
}
