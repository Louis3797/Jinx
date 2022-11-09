package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.highscore.HighScore;
import org.jinx.player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.*;
import static org.jinx.utils.ConsoleColor.WHITE_BOLD_BRIGHT;

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

        System.out.println(BLUE_BOLD +"      _   ___   _   _  __  __");
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

    public void endSequenz(){

        System.out.println(
                        RED_BOLD_BRIGHT +"*%%%%     %%%%%(    %%%%% .%%%%  %%%%%%       %%%%\n" +
                        GREEN_BOLD_BRIGHT +" %%%%*   %%%%%%%    %%%%  .%%%%  %%%%%%%%     %%%%\n" +
                        YELLOW_BOLD_BRIGHT +"  %%%%  #%%% %%%%  %%%%#  .%%%%  %%%%%%%%%*   %%%%\n" +
                        BLUE_BOLD_BRIGHT +"  %%%%/ %%%%  %%%. %%%%   .%%%%  %%%%%  %%%%  %%%%\n" +
                        MAGENTA_BOLD_BRIGHT +"   %%%%%%%%   %%%%%%%%    .%%%%  %%%%%   %%%%%%%%%\n" +
                        CYAN_BOLD_BRIGHT +"   ,%%%%%%     %%%%%%%    .%%%%  %%%%%     %%%%%%%\n" +
                        WHITE_BOLD_BRIGHT +"    %%%%%%     .%%%%%     .%%%%  %%%%%      %%%%%%" + RESET);

        System.out.println("Spielende!");

        Map<String,Integer> winner = new HashMap<>();

        int total = 5;
        for(Player player : pc.getPlayers()){

            winner.put(player.getName(),total);
            total--;
        }

        System.out.println(winner);

        int max = Collections.max(winner.values());

        for(Map.Entry<String, Integer> entry : winner.entrySet()){
            if(max == entry.getValue()){
                System.out.println("Gewinner ist: " + entry.getKey());
            }
        }

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
            endSequenz();
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
            if (!player.isUsedRedo()) {
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
