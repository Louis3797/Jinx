package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.highscore.HighScore;
import org.jinx.player.Player;
import org.jinx.savestate.ResourceManager;
import org.jinx.savestate.SaveData;
import org.jinx.wrapper.SafeScanner;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.*;
import static org.jinx.utils.ConsoleColor.WHITE_BOLD_BRIGHT;

public class GameController implements Serializable {

    private final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    private final PlayerController pc;

    private final List<HighScore> highScoreList;

    private SaveData data;

    public static final long serialVersionUID = 42L;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerController.getPlayerControllerInstance();
        highScoreList = new ArrayList<>();
        data = new SaveData();
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
     * prints endlogo and score
     */
    public void endSequenz() {

        System.out.println(
                RED_BOLD_BRIGHT + "*%%%%     %%%%%(    %%%%% .%%%%  %%%%%%       %%%%\n" +
                        GREEN_BOLD_BRIGHT + " %%%%*   %%%%%%%    %%%%  .%%%%  %%%%%%%%     %%%%\n" +
                        YELLOW_BOLD_BRIGHT + "  %%%%  #%%% %%%%  %%%%#  .%%%%  %%%%%%%%%*   %%%%\n" +
                        BLUE_BOLD_BRIGHT + "  %%%%/ %%%%  %%%. %%%%   .%%%%  %%%%%  %%%%  %%%%\n" +
                        PINK_BOLD_BRIGHT + "   %%%%%%%%   %%%%%%%%    .%%%%  %%%%%   %%%%%%%%%\n" +
                        CYAN_BOLD_BRIGHT + "   ,%%%%%%     %%%%%%%    .%%%%  %%%%%     %%%%%%%\n" +
                        WHITE_BOLD_BRIGHT + "    %%%%%%     .%%%%%     .%%%%  %%%%%      %%%%%%");

        System.out.println("\n" + WHITE_BACKGROUND + "Spielende!" + RESET);

        Map<String, Integer> winner = new HashMap<>();

        for (Player player : pc.getPlayers()) {
            int total = 0;
            for (NumberCard card : player.getCards()) {
                total += Integer.parseInt(card.getName());
            }
            winner.put(player.getName(), total);
        }

        System.out.println(winner);

        int max = Collections.max(winner.values());

        for (Map.Entry<String, Integer> entry : winner.entrySet()) {
            if (max == entry.getValue()) {
                System.out.println("Gewinner ist: " + PINK_BOLD_BRIGHT + entry.getKey() + RESET);
            }
        }

    }

    /**
     * Method starts the game
     */
    public void start() throws Exception {
        SafeScanner scanner = new SafeScanner();

        // Load old Highscores
        getOldHighScores();

        // show startsequenz
        startSequenz();

        Game g1 = new Game();

        pc.addPlayers();


        File saveState = new File("gamestate.save");

        if ((saveState.length() != 0)) {
            System.out.println("Savestate laden?");

            if (scanner.nextYesNoAnswer()) {
                // loads saved state
                data = (SaveData) ResourceManager.load("gamestate.save");
                g1.loadSavestate();
                g1.loadState = true;

                // starts round
                for (int i = data.currentRound; i < 4; i++) {
                    g1.play(i);
                }
            }
        }
        else {
            // initialize without savefile
            g1.initializeDecks();
            // starts round
            for (int i = 1; i < 4; i++) {
                g1.play(i);
            }
        }

        endSequenz();
        writeHighScoreToFile();
        clearSave();

        // clear everything from current round
        pc.getPlayers().clear();
        highScoreList.clear();
        System.out.println("Nochmal spielen?");

        // start a new game
        if (scanner.nextYesNoAnswer()) {
            start();
        }

    }

    /**
     * clears savefile after match ends
     */
    private void clearSave(){
        try {
            new FileOutputStream("gamestate.save").close();
        } catch (IOException e) {
            System.out.println("File nicht vorhanden");
        }

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
            if (!player.isUsedCheats()) {
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
