package org.jinx.history;

import org.jinx.game.PlayerManager;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.savestate.ResourceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileHistoryManager implements IHistoryManager {
    private final PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    public FileHistoryManager() {

    }

    @Override
    public void safeHistory() {
        Date date = new Date();

        for (Player player : playerManager.getPlayers()) {

            String fileName = "Histories/";

            fileName += player.getName();

            if (!player.isHuman()) {
                fileName += ((AutonomousPlayer) player).getDifficulty().name();
            }

            fileName += ".txt";


            List<Player> opponents = new ArrayList<>();

            for (Player p : playerManager.getPlayers()) {
                if (!p.equals(player)) {
                    opponents.add(p);
                }
            }

            PlayerHistory histroy = new PlayerHistory(player.getName(), player.isUsedCheats(), player.getNumberCardHand(),
                    player.getPoints(), player.getLuckyCardHand(), date, opponents);

            player.getMatchHistories().add(histroy);

            ResourceManager.save((Serializable) player.getMatchHistories(), fileName);

        }
    }

    @Override
    public List<PlayerHistory> getHistory(Player player) {
        List<PlayerHistory> result;

        String fileName = "Histories/";

        fileName += player.getName();

        if (!player.isHuman()) {
            fileName += ((AutonomousPlayer) player).getDifficulty().name();
        }

        fileName += ".txt";


        result = (List<PlayerHistory>) ResourceManager.load(fileName);

        return result;
    }
}