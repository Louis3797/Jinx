package org.jinx.login;

import org.jinx.game.PlayerManager;
import org.jinx.wrapper.SafeScanner;

import java.io.Serializable;

import static org.jinx.utils.ConsoleColor.*;

public interface ILoginManager extends Serializable {

    /**
     * Checks credentials of given user in data storage
     *
     * @param username username credential
     * @param password password credential
     * @return Returns true if user exists
     */
    boolean checkCredentials(String username, String password);

    /**
     * Registers user and stores credential in data storage
     *
     * @param username username credential
     * @param password password credential
     * @return Returns true was successfully registered
     */
    boolean registerNewUser(String username, String password);

    /**
     * Checks if user occurs in data storage
     *
     * @param username Given username
     * @return Returns true if user exists
     */
    boolean doesUserExist(String username);

    /**
     * Displays Login
     * @return Returns the username of the user
     */
    default String displayLogin() {
        SafeScanner safeScanner = new SafeScanner();
        PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

        System.out.println(BLUE + "Benutzername:" + RESET);
        String username = safeScanner.nextStringSafe();

        System.out.println(BLUE + "Passwort:" + RESET);
        String password = safeScanner.nextStringSafe();

        if (!checkCredentials(username, password)) {
            System.out.println("Falsches Passwort oder Benutzername, bitte versuchen sie es nochmal");
            return displayLogin();
        }

        boolean isPlayerExisting = playerManager.doesPlayerExist(username);

        if (isPlayerExisting) {
            System.out.println("Der Spieler ist bereits eingeloggt, bitte melden sie sich mit einen anderen Spieler an");
            return displayLogin();
        }

        return username;
    }

    /**
     * Displays view for the registration process
     */
    default void displayRegistration() {

        SafeScanner safeScanner = new SafeScanner();


        System.out.println(BLUE + "Gib den Namen deines Spielers an: " + RESET);

        String username = safeScanner.nextStringSafe();

        while (doesUserExist(username)) {
            System.out.println(RED + "Dieser User existiert bereits, bitte wählen sie ein anderen Namen." + RESET);
            username = safeScanner.nextStringSafe();
        }


        System.out.println(BLUE + "Gib das Password für dein Account an: " + RESET);
        String password = safeScanner.nextStringSafe();

        while (password.length() <= 1) {
            System.out.println(RED + "Ihr password ist zu kurz, bitte wählen sie ein längeres" + RESET);
            password = safeScanner.nextStringSafe();
        }


        boolean success = registerNewUser(username, password);

        if (!success) {
            System.out.println(RED + "Ein Fehler ist aufgetreten, bitte versuchen sie es nochmal" + RESET);
            displayLogin();
        }
        System.out.println(BLUE + "Sie haben sich erfolgreich registriert\nLoggen sie sich nun mit ihren neuen Spieler ein" + RESET);
    }

}
