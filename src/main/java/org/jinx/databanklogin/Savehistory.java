package org.jinx.databanklogin;

import org.jinx.game.PlayerController;
import org.jinx.player.Player;
import org.jinx.wrapper.SafeScanner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Savehistory {

    private final PlayerController pc;

    public Savehistory() {
        pc = PlayerController.getPlayerControllerInstance();
    }

    public void writeHistoriesDatabase() {
        Date date = new Date();
        try {
            PreparedStatement ps = DataConnection.getConnection().prepareStatement("INSERT INTO " +
                    "`spielhistory`(`user`, `kartensumme`,`datum`, `mitspieler` ) VALUES (?,?,?,?)");
            for (Player player : pc.getPlayers()) {
                ps.setString(1, player.getName());
                ps.setInt(2, player.getPoints());
                ps.setString(3, String.valueOf(date));
                String mates = "";
                for (Player player1 : pc.getPlayers()) {

                    if (!player1.getName().equals(player.getName())) {
                        mates += player1.getName() + " ";
                    }
                }
                ps.setString(4, mates);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.WARNING, ex.getMessage());
        }

    }

    /**
     * prints matchhistory of winning player from database
     *
     * @param winner winning player
     */
    public void printDescHistoryDatabase(String winner) {

        SafeScanner scanner = new SafeScanner();

        try {
            String query;

            System.out.println("Geordnete Liste ausgeben?");
            if (scanner.nextYesNoAnswer()) {
                query = "SELECT * FROM spielhistory WHERE user = " + "'" + winner + "'" +
                        " ORDER BY kartensumme DESC";
            }
            else {
                query = "SELECT * FROM spielhistory WHERE user = " + "'" + winner + "'";
            }

            PreparedStatement ps = DataConnection.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    Object object = rs.getObject(columnIndex);
                    System.out.printf("%s, ", object == null ? "NULL" : object.toString());
                }
                System.out.printf("%n");
            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
