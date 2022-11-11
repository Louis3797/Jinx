package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.highscore.HighScore;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.BLUE_BOLD;
import static org.jinx.utils.ConsoleColor.RESET;

public class GameController {

    private final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    private final PlayerController pc;

    private final List<HighScore> highScoreList;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerController.getPlayerControllerInstance();
        highScoreList = new ArrayList<>();
    }

    /**
     * prints gamelogo
     */
    public void startSequenz() {

        System.out.println(BLUE_BOLD + "      _   ___   _   _  __  __");
        System.out.println("     | | |_ _| | \\ | | \\ \\/ /");
        System.out.println("  _  | |  | |  |  \\| |  \\  / ");
        System.out.println(" | |_| |  | |  | |\\  |  /  \\ ");
        System.out.println("  \\___/  |___| |_| \\_| /_/\\_\\" + RESET);
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

        // i is the current round
        for (int i = 1; i < 4; i++) {
            g1.play(i);
        }

        writeHighScoreToFile();

    }

    /**
     * Method reads all highscore data from Highscore.txt and adds it to highScoreList as Highscore Record
     */
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

        // Calculate new Scores of after game and add them to highscore list
        for (Player player : pc.getPlayers()) {
            if (!player.isUsedRedo() && player.getClass() != AutonomousPlayer.class) {
                int score = 0;
                for (NumberCard card : player.getCards()) {
                    score += Integer.parseInt(card.getName());
                }
                highScoreList.add(new HighScore(player.getName(), score));
            }
        }

        // sort highscore list
        highScoreList.sort(Comparator.comparingInt(HighScore::highscore));

        try {
            FileWriter file = new FileWriter("Highscore.txt");

            for (HighScore highScore : highScoreList) {
                file.append(String.valueOf(highScore.highscore())).append(",").append(highScore.playerName()).append("\n");
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
            System.out.println("\t\t" + highscore.playerName() + "\t\t\t   " + highscore.highscore());
        }
        System.out.println();
    }
}
