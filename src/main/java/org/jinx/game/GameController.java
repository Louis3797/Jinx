package org.jinx.game;

import org.jinx.database.JDBCHelper;
import org.jinx.game_state.GameState;
import org.jinx.game_state.ResourceManager;
import org.jinx.highscore.HighScore;
import org.jinx.highscore.HighScoreList;
import org.jinx.history.DatabaseHistoryManager;
import org.jinx.history.FileHistoryManager;
import org.jinx.history.IHistoryManager;
import org.jinx.logging_file_handler.LogFileHandler;
import org.jinx.login.DatabaseLoginManager;
import org.jinx.login.FileLoginManager;
import org.jinx.player.Player;
import org.jinx.wrapper.SafeScanner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.jinx.utils.ConsoleColor.*;

public class GameController implements Serializable {

    public static final long serialVersionUID = 42L;
    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private final PlayerManager pc;

    private final List<HighScore> highScoreList;

    private GameState data;

    private HighScoreList highScore;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerManager.getPlayerManagerInstance();
        highScoreList = new ArrayList<>();
        data = new GameState();
        highScore = new HighScoreList();
        LogFileHandler logFileHandler = LogFileHandler.getInstance();
        logger.addHandler(logFileHandler.getFileHandler());
        logger.setUseParentHandlers(false);

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

        highScore.printHighscore();

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
                    p.printHistory();
                } else {
                    p.printHistory();
                }
            }
        }


    }

    /**
     * Method starts the game
     */

    public void start() throws Exception {
        SafeScanner scanner = new SafeScanner();

        highScore.getOldHighScores();

        // show startsequenz
        startSequenz();

        Game game = new Game();

        data = (GameState) ResourceManager.load("gamestate.save");
        // if savestate exists
        System.out.println(data != null ? "Alten Spielstand laden?" : "");
        IHistoryManager historyManager;
        if ((data != null) && scanner.nextYesNoAnswer()) {

            // loads saved state


            game.loadSaveState();
            game.loadState = true;

            if (data.txt) {
                pc.setLoginManager(new FileLoginManager());
                historyManager = new FileHistoryManager();
            } else {
                pc.setLoginManager(new DatabaseLoginManager());
                historyManager = new DatabaseHistoryManager();
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
                historyManager = new FileHistoryManager();
            } else {

                // checks database connection
                if (JDBCHelper.getConnection() != null) {
                    pc.setLoginManager(new DatabaseLoginManager());
                    pc.setUseFileStorage(false);
                    historyManager = new DatabaseHistoryManager();
                } else {
                    System.out.println("Fehler beim laden der Datenbank. Es wir eine Textdatei genutzt um ihre Daten zu speichern");
                    pc.setLoginManager(new FileLoginManager());
                    pc.setUseFileStorage(true);
                    historyManager = new FileHistoryManager();
                }
            }

            // saves where to save data
            data.txt = pc.isUseFileStorage();


            pc.addPlayers();

            for (Player player : pc.getPlayers()) {
                player.setMatchHistories(historyManager.getHistory(player));
            }
            ResourceManager.save(data, "gamestate.save");
            // initialize without savefile
            game.initializeDecks();
            // starts round
            for (int i = 1; i < 4; i++) {
                game.play(i);
            }
        }

        // safe history
        historyManager.safeHistory();

        endSequenz();
        highScore.writeHighScoreToFile();
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
     * clears savefile after match ends
     */
    private void clearSave() {
        try {
            new FileOutputStream("gamestate.save").close();
        } catch (IOException e) {
            System.out.println("File nicht vorhanden");
        }

    }

}
