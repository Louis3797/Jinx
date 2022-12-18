package org.jinx.game;

import org.jinx.model.IModel;
import org.jinx.player.Player;

import java.util.Queue;

public interface IPlayerManager extends IModel {
    boolean doesPlayerExist(String name);

    Queue<Player> getPlayers();
}
