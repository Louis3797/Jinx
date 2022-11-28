package org.jinx.databanklogin;

import org.jinx.game.PlayerController;
import org.jinx.player.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.BLUE;
import static org.jinx.utils.ConsoleColor.RESET;

public class Savehistory {

    private final PlayerController pc;

    public Savehistory() {
        pc = PlayerController.getPlayerControllerInstance();
    }

    public void savadata() {
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
}
