package org.jinx.game;

import org.jinx.database.JDBCHelper;
import org.jinx.highscore.HighScore;
import org.jinx.history.PlayerHistory;
import org.jinx.history.SaveHistory;
import org.jinx.login.DatabaseLoginManager;
import org.jinx.login.FileLoginManager;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.savestate.GameState;
import org.jinx.savestate.ResourceManager;
import org.jinx.wrapper.SafeScanner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.*;

public class GameController implements Serializable {

    public static final long serialVersionUID = 42L;
    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private final SaveHistory savehistory;

    private final PlayerManager pc;

    private final List<HighScore> highScoreList;

    private GameState data;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerManager.getPlayerManagerInstance();
        highScoreList = new ArrayList<>();
        data = new GameState();
        savehistory = new SaveHistory();

        try {
            logger.addHandler(new FileHandler("logs.log"));

            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

    }

    /**
     * prints gamelogo
     */
    private void startSequenz() {

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
    private void endSequenz() {

        System.out.println(
                RED_BOLD_BRIGHT + "*%%%%     %%%%%(    %%%%% .%%%%  %%%%%%       %%%%\n" +
                        GREEN_BOLD_BRIGHT + " %%%%*   %%%%%%%    %%%%  .%%%%  %%%%%%%%     %%%%\n" +
                        YELLOW_BOLD_BRIGHT + "  %%%%  #%%% %%%%  %%%%#  .%%%%  %%%%%%%%%*   %%%%\n" +
                        BLUE_BOLD_BRIGHT + "  %%%%/ %%%%  %%%. %%%%   .%%%%  %%%%%  %%%%  %%%%\n" +
                        PINK_BOLD_BRIGHT + "   %%%%%%%%   %%%%%%%%    .%%%%  %%%%%   %%%%%%%%%\n" +
                        CYAN_BOLD_BRIGHT + "   ,%%%%%%     %%%%%%%    .%%%%  %%%%%     %%%%%%%\n" +
                        WHITE_BOLD_BRIGHT + "    %%%%%%     .%%%%%     .%%%%  %%%%%      %%%%%%");

        System.out.println("\n" + WHITE_BACKGROUND + "Spielende!" + RESET);

        int highestScore = Integer.MIN_VALUE;

        for (Player p : pc.getPlayers()) {
            if (p.getPoints() >= highestScore) {
                highestScore = p.getPoints();
            }
        }

        for (Player p : pc.getPlayers()) {
            if (p.getPoints() == highestScore) {
                System.out.println("Gewinner ist: " + PINK_BOLD_BRIGHT + p.getName() + RESET);
                if (pc.isUseFileStorage()) {
                    printDescHistory(p);
                } else {
                    savehistory.printDescHistoryDatabase(p.getName());
                }
            }
        }


    }

    /**
     * Method starts the game
     */

    public void start() throws Exception {
        SafeScanner scanner = new SafeScanner();

        getOldHighScores();

        // show startsequenz
        startSequenz();

        Game game = new Game();

        data = (GameState) ResourceManager.load("gamestate.save");
        // if savestate exists
        System.out.println(data != null ? "Savestate laden?" : "");
        if ((data != null) && scanner.nextYesNoAnswer()) {

            // loads saved state


            game.loadSaveState();
            game.loadState = true;

            if (data.txt) {
                pc.setLoginManager(new FileLoginManager());
            } else {
                pc.setLoginManager(new DatabaseLoginManager());
            }

            // starts round
            for (int i = data.currentRound; i < 4; i++) {
                game.play(i);
            }


        } else {

            data = new GameState();

            System.out.println("[1] Textdatei\n[2] Datenbank");
            int choice = scanner.nextIntInRange(1, 2);
            if (choice == 1) {
                pc.setLoginManager(new FileLoginManager());
                pc.setUseFileStorage(true);
            } else {

                // checks database connection
                if (JDBCHelper.getConnection() != null) {
                    pc.setLoginManager(new DatabaseLoginManager());
                    pc.setUseFileStorage(false);
                } else {
                    System.out.println("Fehler beim laden der Datenbank. Es wir eine Textdatei genutzt um ihre Daten zu speichern");
                    pc.setLoginManager(new FileLoginManager());
                    pc.setUseFileStorage(true);
                }
            }

            // saves where to save data
            data.txt = pc.isUseFileStorage();


            pc.addPlayers();
            ResourceManager.save(data, "gamestate.save");
            // initialize without savefile
            game.initializeDecks();
            // starts round
            for (int i = 1; i < 4; i++) {
                game.play(i);
            }
        }

        // writes and deletes relevant data to and from files
        if (pc.isUseFileStorage()) {
            writeHistories();
        } else {
            savehistory.writeHistoriesDatabase();
        }

        endSequenz();
        writeHighScoreToFile();
        clearSave();
        game.getFh().close();

        // clear everything from current round
        pc.getPlayers().clear();
        highScoreList.clear();

        // look at replay of last round
        System.out.println("Replay anschauen?");
        if (scanner.nextYesNoAnswer()) {
            replay();
        }

        System.out.println("Nochmal spielen?");

        // start a new game
        if (scanner.nextYesNoAnswer()) {
            start();
        }

    }

    /**
     * prints replay of last game
     */
    private void replay() {
        try {
            List<String> content = Files.readAllLines(Path.of("Spielzuege.log"));
            for (String line : content) {
                System.out.println(line);
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * prints descending match-history of winner
     *
     * @param player winner
     */
    private void printDescHistory(Player player) {
        SafeScanner safeScanner = new SafeScanner();

        List<PlayerHistory> history = player.getMatchHistories();

        System.out.println("Liste nach Punkten geordnet ausgeben?");
        // sorts list by points in desc order
        if (safeScanner.nextYesNoAnswer()) {
            history.sort(Comparator.comparingInt(PlayerHistory::cardSum));
        }

        // prints history of player
        for (PlayerHistory h : history) {
            System.out.println(WHITE_BOLD_BRIGHT + "" + h.toString() + RESET);
        }


    }

    /**
     * writes matchhistory for all players including bots
     */
    private void writeHistories() {

        Date date = new Date();
        Path path;


        for (Player player : pc.getPlayers()) {
            try {
                if (!player.isHuman()) {
                    // history with bot name
                    path = Paths.get("Histories/" + player.getName() + "-" + ((AutonomousPlayer) player).getDifficulty() + ".txt");
                } else {
                    // history with player name
                    path = Paths.get("Histories/" + player.getName() + ".txt");
                }


                List<Player> opponents = new ArrayList<>();

                for (Player p : pc.getPlayers()) {
                    if (!p.equals(player)) {
                        opponents.add(p);
                    }
                }

                PlayerHistory histroy = new PlayerHistory(player.getName(), player.isUsedCheats(), player.getNumberCardHand(),
                        player.getPoints(), player.getLuckyCardHand(), date, opponents);

                player.getMatchHistories().add(histroy);

                System.out.println(path);
                ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(path.toUri())));

                oos.writeObject(player.getMatchHistories());

                oos.close();

            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }


    /**
     * clears savefile after match ends
     */
    private void clearSave() {
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
            logger.warning(e.getMessage());
        }
    }

    /**
     * Method writes Highscore of Player to highscore.txt file
     */
    private void writeHighScoreToFile() {

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
    private void printHighscore() {

        System.out.println("---------- Highscores ----------");
        for (HighScore highscore : highScoreList) {
            System.out.println("\t\t" + highscore.playerName() + "\t\t\t   " + highscore.highscore());
        }
        System.out.println();
    }
}
