package org.jinx.history;

import org.jinx.database.JDBCHelper;
import org.jinx.game.PlayerManager;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.wrapper.SafeScanner;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SaveHistory {

    private final PlayerManager pc;

    private static final Logger logger = Logger.getLogger(SaveHistory.class.getName());

    public SaveHistory() {
        pc = PlayerManager.getPlayerManagerInstance();

        try {
            logger.addHandler(new FileHandler("logs.log"));
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * writes match-histories to database
     */
    public void writeHistoriesDatabase() {
        Date date = new Date();

        Connection con = JDBCHelper.getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO " +
                    "`spielhistory`(`user`, `kartensumme`,`datum`, `mitspieler`, `bot` ) VALUES (?,?,?,?,?)");

            for (Player player : pc.getPlayers()) {

                ps.setString(1, player.getName());
                ps.setInt(2, player.getPoints());
                ps.setString(3, String.valueOf(date));
                StringBuilder mates = new StringBuilder();

                for (Player player1 : pc.getPlayers()) {

                    if (!player1.getName().equals(player.getName())) {
                        mates.append(player1.getName()).append(" ");
                    }
                }

                ps.setString(4, mates.toString());
                ps.setString(5, player.isHuman() ? "no" : ((AutonomousPlayer) player).getDifficulty().toString());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            logger.warning(ex.getMessage());
        } finally {
            JDBCHelper.closePreparedStatement(ps);
            JDBCHelper.closeConnection(con);
        }

    }

    /**
     * prints match-history of winning player from database
     *
     * @param winner winning player
     */
    public void printDescHistoryDatabase(String winner) {

        SafeScanner scanner = new SafeScanner();

        Connection con = JDBCHelper.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query;

            System.out.println("Geordnete Liste ausgeben?");
            // sql querys
            if (scanner.nextYesNoAnswer()) {
                query = "SELECT * FROM spielhistory WHERE user = " + "'" + winner + "'" +
                        " ORDER BY kartensumme DESC";
            } else {
                query = "SELECT * FROM spielhistory WHERE user = " + "'" + winner + "'";
            }

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // print database tables
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    Object object = rs.getObject(columnIndex);
                    System.out.printf("%s, ", object == null ? "NULL" : object.toString());
                }
                System.out.printf("%n");
            }


        } catch (SQLException ex) {
            logger.warning(ex.getMessage());
        } finally {
            JDBCHelper.closePreparedStatement(ps);
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closeConnection(con);
        }
    }
}
