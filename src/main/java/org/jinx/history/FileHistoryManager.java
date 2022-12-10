package org.jinx.history;

import org.jinx.game.PlayerManager;
import org.jinx.game_state.ResourceManager;
import org.jinx.logging_file_handler.LogFileHandler;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class FileHistoryManager implements IHistoryManager {

    private static final Logger logger = Logger.getLogger(FileHistoryManager.class.getName());
    private final PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    public FileHistoryManager() {

        LogFileHandler logFileHandler = LogFileHandler.getInstance();
        logger.addHandler(logFileHandler.getFileHandler());
        logger.setUseParentHandlers(false);

        // Check if dir Histories exists
        Path path = Paths.get("Histories");

        if (!Files.exists(path)) {
            logger.warning("Ordner Histories existiert nicht");
            try {
                Files.createDirectory(path);
                logger.info("Histories Ordner wurde angelegt");
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
            }
        }
    }

    @Override
    public void safeHistory() {
        Date date = new Date();

        for (Player player : playerManager.getPlayers()) {

            String fileName = "Histories/";

            fileName += player.getName();

            if (!player.isHuman()) {
                fileName += "-" + ((AutonomousPlayer) player).getDifficulty().name();
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

        String fileName = "Histories/";

        fileName += player.getName();

        if (!player.isHuman()) {
            fileName += "-" + ((AutonomousPlayer) player).getDifficulty().name();
        }

        fileName += ".txt";

        // load Histories
        // Note that ResourceManager.load can return null
        // so result can be null
        List<PlayerHistory> result = (List<PlayerHistory>) ResourceManager.load(fileName);

        return result != null ? result : new ArrayList<>();
    }
}