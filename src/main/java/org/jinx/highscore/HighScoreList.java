package org.jinx.highscore;

import org.jinx.game.PlayerManager;
import org.jinx.model.IModel;
import org.jinx.player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class HighScoreList implements IModel {
    private static final Logger logger = Logger.getLogger(HighScoreList.class.getName());

    private final List<HighScore> highScoreList;
    private final PlayerManager pc;

    public HighScoreList() {
        highScoreList = new ArrayList<>();
        pc = PlayerManager.getPlayerManagerInstance();
    }

    public void getOldHighScores() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Highscore.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                HighScore highScore = new HighScore(data[1], Integer.parseInt(data[0]));
                highScoreList.add(highScore);
            }
            highScoreList.sort(Comparator.comparingInt(HighScore::highscore).reversed());
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Method writes Highscore of Player to highscore.txt file
     */
    public void writeHighScoreToFile() {

        // Calculate new Scores of after game and add them to highscore list
        for (Player player : pc.getPlayers()) {
            if (!player.isUsedCheats()) {
                int score = player.getPoints();
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
            logger.warning(e.getMessage());
        }
    }

    /**
     * Method prints highscore at start of game
     */
    public void printHighscore() {

        System.out.println("---------- Highscores ----------");
        for (HighScore highscore : highScoreList) {
            System.out.println("\t\t" + highscore.playerName() + "\t\t\t   " + highscore.highscore());
        }
        System.out.println();
    }

    public List<HighScore> getHighScoreList() {
        return highScoreList;
    }


}
