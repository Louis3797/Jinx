package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.database.JDBCHelper;
import org.jinx.highscore.HighScore;
import org.jinx.history.SaveHistory;
import org.jinx.login.DatabaseLoginManager;
import org.jinx.login.FileLoginManager;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.savestate.ResourceManager;
import org.jinx.savestate.SaveData;
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

    private SaveData data;

    /**
     * Basic Constructor of the GameController class
     */
    public GameController() {
        pc = PlayerManager.getPlayerControllerInstance();
        highScoreList = new ArrayList<>();
        data = new SaveData();
        savehistory = new SaveHistory();

        try {
            logger.addHandler(new FileHandler("logs.log"));
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

        Map<Player, Integer> winner = new HashMap<>();

        for (Player player : pc.getPlayers()) {
            int total = 0;
            for (NumberCard card : player.getNumberCardHand()) {
                total += Integer.parseInt(card.getName());
            }
            winner.put(player, total);
        }


        int max = Collections.max(winner.values());

        for (Map.Entry<Player, Integer> entry : winner.entrySet()) {
            if (max == entry.getValue()) {
                System.out.println("Gewinner ist: " + PINK_BOLD_BRIGHT + entry.getKey().getName() + RESET);
                if (pc.isFileStorage()) {
                    printDescHistory(entry.getKey());
                } else {
                    savehistory.printDescHistoryDatabase(entry.getKey().getName());
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

        Game g1 = new Game();


        File saveState = new File("gamestate.save");

        // if savestate exists
        System.out.println(saveState.length() != 0 ? "Savestate laden?" : "");
        if ((saveState.length() != 0) && scanner.nextYesNoAnswer()) {

            // loads saved state
            data = (SaveData) ResourceManager.load("gamestate.save");
            g1.loadSaveState();
            g1.loadState = true;

            if (data.txt) {
                pc.setLoginManager(new FileLoginManager());
            } else {
                pc.setLoginManager(new DatabaseLoginManager());
            }

            // starts round
            for (int i = data.currentRound; i < 4; i++) {
                g1.play(i);
            }

        } else {

            System.out.println("[1] Textdatei\n[2] Datenbank");
            int choice = scanner.nextIntInRange(1, 2);
            if (choice == 1) {
                pc.setLoginManager(new FileLoginManager());
                pc.setFileStorage(true);
            }

            // checks database connection
            if (JDBCHelper.getConnection() == null) {
                pc.setLoginManager(new DatabaseLoginManager());
                pc.setFileStorage(false);
            }

            // saves where to save data
            data.txt = choice == 1;
            ResourceManager.save(data, "gamestate.save");

            pc.addPlayers();
            // initialize without savefile
            g1.initializeDecks();
            // starts round
            for (int i = 1; i < 4; i++) {
                g1.play(i);
            }
        }

        // writes and deletes relevant data to and from files

        if (pc.isFileStorage()) {
            writeHistories();
        } else {
            savehistory.writeHistoriesDatabase();
        }

        endSequenz();
        writeHighScoreToFile();
        clearSave();
        g1.getFh().close();

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

        SafeScanner scanner = new SafeScanner();

        try {

            Path path;

            if (!player.isHuman()) {
                // prints bot history
                path = Paths.get("Histories/" + ((AutonomousPlayer) player).getDifficulty() + ".txt");

            } else {
                // prints player history
                path = Paths.get("Histories/" + player.getName() + ".txt");
            }

            List<String> content = Files.readAllLines(path);
            ArrayList<String> lines = new ArrayList<>();
            ArrayList<ArrayList<String>> paragraphs = new ArrayList<>();

            // reads lines and cuts off at "-"
            // all info before that is stored in a string
            // this string is added to a list which
            // is added to a 2d list
            for (String line : content) {
                if (line.equals("-")) {
                    paragraphs.add(lines);
                    lines = new ArrayList<>();
                } else {
                    lines.add(line);
                }
            }

            System.out.println("Liste nach Punkten geordnet ausgeben?");
            // sorts list by points in desc order
            if (scanner.nextYesNoAnswer()) {
                paragraphs.sort((x, y) -> {
                    for (int i = 0; i < Math.min(x.size(), y.size()); i++) {
                        if (!Objects.equals(x.get(i), y.get(i))) {
                            String[] data = x.get(1).split("Kartensumme: ");
                            String[] dataY = y.get(1).split("Kartensumme: ");
                            return Integer.parseInt(dataY[1]) - Integer.parseInt(data[1]);
                        }
                    }
                    return Integer.compare(x.size(), y.size());
                });
            }

            // prints history of player
            for (ArrayList<String> list : paragraphs) {
                System.out.println(WHITE_BOLD_BRIGHT + "" + list + RESET);
            }

        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

    }

    /**
     * writes matchhistory for all players including bots
     */
    private void writeHistories() {

        Date date = new Date();
        FileWriter file;

        for (Player player : pc.getPlayers()) {
            try {
                if (!player.isHuman()) {
                    // history with bot name
                    file = new FileWriter("Histories/" + ((AutonomousPlayer) player).getDifficulty() + ".txt", true);
                } else {
                    // history with player name
                    file = new FileWriter("Histories/" + player.getName() + ".txt", true);
                }

                // appends relevant game information
                file.append("Spieler: ").append(player.isHuman() ? player.getName() : "bot-" + player.getName() + "-" +
                        ((AutonomousPlayer) player).getDifficulty()).append("\n");
                file.append("Kartensumme: ").append(String.valueOf(player.getPoints())).append("\n");
                file.append("Datum: ").append(String.valueOf(date)).append("\n");
                file.append("Mitspieler: ");

                // appends bot names
                for (Player player1 : pc.getPlayers()) {
                    if (!player1.getName().equals(player.getName())) {
                        if (!player1.isHuman()) {
                            file.append("bot-").append(player1.getName())
                                    .append("-")
                                    .append(String.valueOf(((AutonomousPlayer) player1).getDifficulty()))
                                    .append(" ");
                        } else {
                            file.append(player1.getName()).append(" ");
                        }

                    }
                }

                file.append("\n-\n");

                file.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
