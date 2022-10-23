package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class GameController {

    private final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    private final PlayerController pc;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerController.getPlayerControllerInstance();

    }

    /**
     * Method starts the game
     */
    public void start() {
        printHighscore();

        Game g1 = new Game();

        pc.addPlayers();

        g1.fillLuckyDeck();

        // i is the current round
        for (int i = 1; i < 4; i++) {
            g1.play(i);
        }

        writeHighScoreToFile();

    }

    /**
     * Method writes Highscore of Player to highscore.txt file
     */
    private void writeHighScoreToFile() {
        // What if the player makes new Highscore ?
        // We need to rewrite the file everytime

        // Example

        // Player one makes new Highscore of 100 points

        // old list is

        // Player 2,30
        // Player 1,29

        // new list should be
        // Player 1,100
        // Player 2,30
        // Player 1,29
        try {
            FileWriter myWriter = new FileWriter("Highscore.txt", true);

            for (Player player : pc.getPlayers()) {
                int total = 0;
                for (NumberCard card : player.getCards()) {
                    total += Integer.parseInt(card.getName());
                }
                myWriter.append(String.valueOf(total)).append(",").append(player.getName()).append("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Method prints highscore at start of game
     */
    private void printHighscore() {
        try {
            System.out.println("HIGHSCORES:\nSCORE   PLAYER");
            BufferedReader br = new BufferedReader(new FileReader("Highscore.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

    }
}
