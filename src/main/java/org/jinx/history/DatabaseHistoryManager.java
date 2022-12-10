package org.jinx.history;

import org.jinx.database.JDBCHelper;
import org.jinx.game.PlayerManager;
import org.jinx.logging_file_handler.LogFileHandler;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHistoryManager implements IHistoryManager {

    private static final Logger logger = Logger.getLogger(DatabaseHistoryManager.class.getName());
    private final PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    public DatabaseHistoryManager() {
        LogFileHandler logFileHandler = LogFileHandler.getInstance();
        logger.addHandler(logFileHandler.getFileHandler());
        logger.setUseParentHandlers(false);
    }

    @Override
    public void safeHistory() {
        Date date = new Date();

        Connection con = JDBCHelper.getConnection();
        PreparedStatement ps = null;


        for (Player player : playerManager.getPlayers()) {
            List<Player> opponents = new ArrayList<>();

            // create opponent list
            for (Player p : playerManager.getPlayers()) {
                if (!p.equals(player)) {
                    opponents.add(p);
                }
            }

            // create history obj
            PlayerHistory history = new PlayerHistory(player.getName(), player.isUsedCheats(), player.getNumberCardHand(),
                    player.getPoints(), player.getLuckyCardHand(), date, opponents);

            // safe history in db
            try {

                if (player.isHuman()) {
                    ps = con.prepareStatement("Insert Into match_history (username, history_object) VALUES (?,?)");
                    ps.setString(1, player.getName());

                    ps.setObject(2, history);
                } else {
                    ps = con.prepareStatement("Insert Into match_history (username, bot_difficulty, history_object) VALUES (?,?,?)");
                    ps.setString(1, player.getName());
                    ps.setString(2, ((AutonomousPlayer) player).getDifficulty().name());
                    ps.setObject(3, history);
                }
                ps.execute();


            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }
        }

        JDBCHelper.closePreparedStatement(ps);
        JDBCHelper.closeConnection(con);
    }

    @Override
    public List<PlayerHistory> getHistory(Player player) {

        Connection con = JDBCHelper.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PlayerHistory> result = new ArrayList<>();

        try {

            if (player.isHuman()) {
                ps = con.prepareStatement("SELECT history_object FROM match_history WHERE username = ?" +
                        " AND bot_difficulty = NULL");
                ps.setString(1, player.getName());
            } else {
                ps = con.prepareStatement("SELECT history_object FROM match_history WHERE username = ?" +
                        " AND bot_difficulty = ?");
                ps.setString(1, player.getName());
                ps.setString(2, ((AutonomousPlayer) player).getDifficulty().name());
            }

            rs = ps.executeQuery();


            ObjectInputStream objectIn = null;

            while (rs.next()) {
                try {
                    byte[] buf = rs.getBytes(1);
                    if (buf != null) {
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                        PlayerHistory h = (PlayerHistory) objectIn.readObject();
                        result.add(h);
                    }
                } catch (ClassCastException | IOException | ClassNotFoundException e) {
                    logger.warning(e.getMessage());
                }
            }

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        } finally {
            JDBCHelper.closePreparedStatement(ps);
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closeConnection(con);
        }
        System.out.println(Arrays.toString(result.toArray()));
        return result;
    }
}
