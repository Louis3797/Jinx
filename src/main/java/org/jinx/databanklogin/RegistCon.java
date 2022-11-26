package org.jinx.databanklogin;


import org.jinx.wrapper.SafeScanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.BLUE;
import static org.jinx.utils.ConsoleColor.RESET;

public class RegistCon {

    private final SafeScanner safeScanner;

    public RegistCon() {
        safeScanner = new SafeScanner();
    }

    /**
     * Method to register User in database
     */
    public void regist2() {
        DataConnection dataConnection = new DataConnection();

        System.out.println(BLUE + "Benutzername:" + RESET);
        String playerName = safeScanner.nextStringSafe();

        if (dataConnection.checkUsername(playerName)) {
            System.out.println(BLUE + "Benutzer " + playerName + " existiert bereits.\nBitte geben sie ein anderen Namen ein:" + RESET);
            regist2();
        } else {
            System.out.println(BLUE + "Passwort:" + RESET);
            String passwort = safeScanner.nextStringSafe();
            try {
                PreparedStatement ps = DataConnection.getConnection().prepareStatement("INSERT INTO " +
                        "`user`(`username`, `pass`) VALUES (?,MD5(?))");
                ps.setString(1, playerName);
                ps.setString(2, passwort);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Method to login in the System
     */
    public void loginSystem() {
        System.out.println(BLUE + "Benutzername:" + RESET);
        String userName = safeScanner.nextStringSafe();

        System.out.println(BLUE + "Passwort:" + RESET);
        String pass = safeScanner.nextStringSafe();

        DataConnection dataConnection = new DataConnection();
        if (dataConnection.checkUserandPassword(userName, pass)) {
            System.out.println("logged");
        } else {
            System.out.println("Das hat leider nicht funktioniert. Versichere dich, " +
                    "dass du die richtige Username und das richtige Passwort verwendest.");
            loginSystem();
        }
    }

}
