package org.jinx.game;

import org.jinx.player.AgentDifficulty;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.wrapper.SafeScanner;

import java.util.*;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.BLUE;
import static org.jinx.utils.ConsoleColor.RESET;

/**
 * The PlayerController manages all players.
 * It can register and save players and
 * at the same time manages which player is currently on the turn.
 * <p>
 * PlayerController uses Singleton Pattern
 */
public class PlayerController {
    private final Logger LOGGER = Logger.getLogger(PlayerController.class.getName());

    /**
     * Instance for the Singleton pattern of the PlayerController
     */
    private static final PlayerController playerControllerInstance = new PlayerController();

    private final Queue<Player> players;
    /**
     * Stores which player is currently on the turn
     */
    private Player currentPlayer;

    /**
     * Wrapper for the Scanner
     */
    private final SafeScanner safeScanner;

    /**
     * Standard Constructor for the Player Controller
     */
    private PlayerController() {
        players = new LinkedList<>();
        currentPlayer = null;
        safeScanner = new SafeScanner();
    }

    /**
     * adds players to game
     */
    public void addPlayers() {
        System.out.println(BLUE + "Um das Spiel zu spielen brauchen sie mindestens 2-4 Spieler." + RESET);

        while (players.size() != 4) {

            // is max player number is reached?

            // add player until min player number is reached
            if (players.size() < 2) {
                addOnePlayer();
            } else {
                System.out.println(BLUE + "Wollen sie noch ein weiteren Spieler hinzufügen?\n[y,yes,ja | n,no,nein]" + RESET);

                boolean oneMorePlayer = safeScanner.nextYesNoAnswer();

                if (!oneMorePlayer) {
                    break;
                }

            }
        }

        System.out.println(BLUE + "Wollen sie die Spieler durchmischen?" + RESET);

        boolean shufflePlayer = safeScanner.nextYesNoAnswer();
        if (shufflePlayer) {
            shufflePlayerOrder();
        }
    }

    /**
     * Adds Player to the queue
     */
    public void addOnePlayer() {

        System.out.println(BLUE + "Gib deinen Spieler einen Namen:" + RESET);

        String playerName;
        boolean isPlayerExisting;

        do {
            playerName = safeScanner.nextStringSafe();

            isPlayerExisting = doesPlayerExist(playerName);

            if (isPlayerExisting) {
                System.out.println(BLUE + "Spieler " + playerName + " existiert bereits.\nBitte geben sie ein anderen Namen ein:" + RESET);
            }
        } while (isPlayerExisting);

        System.out.println("Wollen sie das der Spieler von alleine spielt?\n[y,yes,ja | n,no,nein]");

        if (safeScanner.nextYesNoAnswer()) {

            System.out.println("Welche Schwierigkeit wollen sie der KI geben?");
            for (int i = 0; i < AgentDifficulty.values().length; i++) {
                System.out.println(i + 1 + ": " + AgentDifficulty.values()[i].name());
            }

            AgentDifficulty difficulty = AgentDifficulty.values()[safeScanner.nextIntInRange(0 + 1, AgentDifficulty.values().length) - 1];
            players.add(new AutonomousPlayer(playerName, difficulty));
        } else {
            players.add(new Player(playerName));
        }

        System.out.println(BLUE + playerName + " wurde dem Spiel hinzugefügt!" + RESET);
    }

    /**
     * Helper method that checks if Player with given name already exists
     *
     * @param name Name we want to check
     * @return true if yes else false
     */
    private boolean doesPlayerExist(String name) {

        for (Player player : players) {
            if (player.getName().equals(name))
                return true;
        }

        return false;
    }

    /**
     * Use this method when you want to move on to the next player.
     */
    public void next() {

        // Check if queue is empty
        if (players.isEmpty()) {
            LOGGER.warning("Queue is empty!");
            return;
        }

        // Removes and stores Player at the first place of the queue in currentPlayer
        // and adds him to the end of the queue
        currentPlayer = players.poll();
        players.add(currentPlayer);
    }

    /**
     * Shuffles the players so that
     * they are in a different order than they were entered
     */
    public void shufflePlayerOrder() {

        // check if players is empty
        if (players.isEmpty()) {
            LOGGER.warning("Queue is empty");
            return;
        }

        // temps array to shuffle the players because we cant shuffle a queue
        List<Player> tempPlayers = new ArrayList<>(players);
        players.clear();

        // shuffle players
        Collections.shuffle(tempPlayers);

        players.addAll(tempPlayers);
    }

    /**
     * Returns an instance of the PlayerController
     *
     * @return object of a playerController
     */
    public static PlayerController getPlayerControllerInstance() {
        return playerControllerInstance;
    }

    /**
     * Prints the hands of all players
     */
    public void printPlayerHands() {
        for (Player player : getPlayers()) {
            System.out.println("Aktuelle Hand von " + player.getName());
            player.printHand();
            System.out.println();
        }
    }

    /* ---------- Getter and Setter Methods ---------- */

    public Queue<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
