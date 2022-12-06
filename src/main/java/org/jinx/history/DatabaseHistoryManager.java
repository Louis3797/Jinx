package org.jinx.history;

import org.jinx.database.JDBCHelper;
import org.jinx.game.PlayerManager;
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

    private static final Logger logger = Logger.getLogger(FileHistoryManager.class.getName());
    private final PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    public DatabaseHistoryManager() {

    }

    @Override
    public void safeHistory() {
        Date date = new Date();

        Connection con = JDBCHelper.getConnection();
        PreparedStatement ps = null;


        for (Player player : playerManager.getPlayers()) {
            List<Player> opponents = new ArrayList<>();

            for (Player p : playerManager.getPlayers()) {
                if (!p.equals(player)) {
                    opponents.add(p);
                }
            }

            PlayerHistory histroy = new PlayerHistory(player.getName(), player.isUsedCheats(), player.getNumberCardHand(),
                    player.getPoints(), player.getLuckyCardHand(), date, opponents);

            player.getMatchHistories().add(histroy);
            try {

                ps = con.prepareStatement("INSERT INTO " + "`matchhistory` (`user`, `java_object`) VALUES (?,?) " +
                        "ON DUPLICATE KEY UPDATE `java_object` = ?");


                String playerName = player.getName();

                if (!player.isHuman()) {
                    playerName += "-" + ((AutonomousPlayer) player).getDifficulty().name();
                }

                ps.setString(1, playerName);
                ps.setObject(2, player.getMatchHistories());
                ps.setObject(3, player.getMatchHistories());
                ps.executeUpdate();


            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            } finally {
                JDBCHelper.closePreparedStatement(ps);
                JDBCHelper.closeConnection(con);
            }
        }
    }

    @Override
    public List<PlayerHistory> getHistory(Player player) {

        Connection con = JDBCHelper.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PlayerHistory> result = new ArrayList<>();

        String playerName = player.getName();
        if (!player.isHuman()) {
            // history with bot name
            playerName += "-" + ((AutonomousPlayer) player).getDifficulty().name();
        }
        try {

            ps = con.prepareStatement("SELECT `java_object` FROM spielhistory WHERE user = " + "`" + playerName + "`");

            rs = ps.executeQuery();
            rs.next();

            System.out.println(Arrays.toString(rs.getBytes(1)));
            // Object object = rs.getObject(1);

            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            result = (List<PlayerHistory>) objectIn.readObject();

        } catch (IOException | ClassNotFoundException | SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        } finally {
            JDBCHelper.closePreparedStatement(ps);
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closeConnection(con);
        }
        return result;
    }
}
