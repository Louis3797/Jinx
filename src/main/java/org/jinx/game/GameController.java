package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.highscore.HighScore;
import org.jinx.player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class GameController {

    private final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    private final PlayerController pc;

    private List<HighScore> highScoreList;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerController.getPlayerControllerInstance();
        highScoreList = new ArrayList<>();
    }

    public void startSequenz() {

        System.out.println("      _   ___   _   _  __  __");
        System.out.println("     | | |_ _| | \\ | | \\ \\/ /");
        System.out.println("  _  | |  | |  |  \\| |  \\  / ");
        System.out.println(" | |_| |  | |  | |\\  |  /  \\ ");
        System.out.println("  \\___/  |___| |_| \\_| /_/\\_\\");
        System.out.println("\n");

        printHighscore();

        System.out.println("Press any Key to play");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

    /**
     * Method starts the game
     */
    public void start() {

        // Load old Highscores
        getOldHighScores();

        // show startsequenz
        startSequenz();

        Game g1 = new Game();

        pc.addPlayers();

        g1.fillLuckyDeck();

        // i is the current round
        for (int i = 1; i < 4; i++) {
            g1.play(i);
        }

        writeHighScoreToFile();

    }

    private void getOldHighScores() {


        try {
            BufferedReader reader = new BufferedReader(new FileReader("Highscore.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                HighScore highScore = new HighScore(data[1], Integer.parseInt(data[0]));
                highScoreList.add(highScore);
            }

        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
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
            FileWriter file = new FileWriter("Highscore.txt");

            for (Player player : pc.getPlayers()) {
                int total = 0;
                for (NumberCard card : player.getCards()) {
                    total += Integer.parseInt(card.getName());
                }
                file.append(String.valueOf(total)).append(",").append(player.getName()).append("\n");
            }
            file.close();
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Method prints highscore at start of game
     */
    private void printHighscore() {

        System.out.println("---------- Highscores ----------");
        for (HighScore highscore : highScoreList) {
            System.out.println("\t\t" +highscore.playerName() + "\t\t\t   " + highscore.highscore());
        }
        System.out.println();
    }
}
