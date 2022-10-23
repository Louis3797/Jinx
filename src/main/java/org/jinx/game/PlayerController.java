package org.jinx.game;

import org.jinx.player.Player;

import java.util.*;
import java.util.logging.Logger;

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
     * Standard Constructor for the Player Controller
     */
    private PlayerController() {
        players = new LinkedList<>();
        currentPlayer = null;
    }

    public void addPlayers() {


        Scanner input = new Scanner(System.in);

        System.out.println("Um das Spiel zu spielen brauchen sie mindestens 2-4 Spieler.");

        while (players.size() != 4) {

            // is max player number is reached?

            // if min player number is reached
            if (players.size() >= 2) {
                System.out.println("Wollen sie noch ein weiteren Spieler hinzufügen?\n[yes, no]:");

                while (!input.hasNext("^yes|no$")) {

                    input.nextLine();
                    System.out.println("Bitte geben sie yes oder no ein um weiter zu machen:");
                }

                // no case
                if (input.next().equals("no")) {
                    return;
                }
            }

            addOnePlayer();
        }

        System.out.println(players);
    }

    /**
     * Adds Player to the queue
     */
    public void addOnePlayer() {

        System.out.println("Gib deinen Spieler einen Namen:");

        Scanner input = new Scanner(System.in);

        String playerName;
        boolean temp = false;

        do {
            playerName = input.nextLine();

            temp = doesPlayerExist(playerName);

            if (temp) {
                System.out.println("Spieler " + playerName + " existiert bereits.\nBitte geben sie ein anderen Namen ein:");
            }
        }
        while (temp);

        players.add(new Player(playerName));

        System.out.println(playerName + " wurde dem Spiel hinzugefügt!");
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

    /* ---------- Getter and Setter Methods ---------- */

    public Queue<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
