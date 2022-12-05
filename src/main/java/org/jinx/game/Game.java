package org.jinx.game;

import org.jinx.card.LuckyCard;
import org.jinx.card.LuckyCardNames;
import org.jinx.card.NumberCard;
import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.dice.Dice;
import org.jinx.field.Field;
import org.jinx.formatter.PlayMoveFileFormatter;
import org.jinx.player.AgentDifficulty;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.savestate.ResourceManager;
import org.jinx.savestate.SaveData;
import org.jinx.wrapper.SafeScanner;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class Game implements Serializable {

    private final PlayerManager pc = PlayerManager.getPlayerManagerInstance();
    private final Dice dice;
    private NumberCardStack numberCardsDeck;
    private LuckyCardStack luckyCardDeck;

    private final Field field = Field.getFieldInstance();

    private final SafeScanner safeScanner;
    public boolean loadState = false;
    private SaveData data;

    private transient Logger logger;
    private FileHandler fh;


    public Game() {

        data = new SaveData();

        dice = new Dice();

        safeScanner = new SafeScanner();

        initLogger();

    }

    /**
     * loads savestate of interrupted game
     *
     * @throws Exception file exception
     */
    public void loadSaveState() throws Exception {
        data = (SaveData) ResourceManager.load("gamestate.save");

        for(Player key : data.map.keySet()){
            pc.getPlayers().add(key);
        }

        numberCardsDeck = data.deck;
        luckyCardDeck = data.luckyDeck;

        while(pc.getCurrentPlayer() != data.currentPlayer){
            pc.next();
        }

        for (int i = 0; i < field.getFieldSize(); i++) {
            field.getField()[i] = data.field.getField()[i];
        }
    }

    /**
     * initializes decks not loaded from file
     */
    public void initializeDecks() {
        luckyCardDeck = new LuckyCardStack();

        numberCardsDeck = new NumberCardStack();

        data.deck = numberCardsDeck;
        data.luckyDeck = luckyCardDeck;

        data.map = new HashMap<>();

        for(Player player : pc.getPlayers()){
            data.map.put(player,player.getNumberCardHand());
        }

        ResourceManager.save(data, "gamestate.save");
    }

    /**
     * initializes logger
     */
    private void initLogger() {

        try {
            logger = Logger.getLogger(getClass().getName());
            fh = new FileHandler("Spielzuege.log");
            logger.addHandler(fh);
            fh.setFormatter(new PlayMoveFileFormatter());
            logger.setUseParentHandlers(false);
        } catch (IOException ignored) {

        }

    }

    /**
     * This method controls the gameflow for each round
     */
    public void play(int currentRound) throws Exception {

        data.currentRound = currentRound;
        ResourceManager.save(data,"gamestate.save");

        if(loadState){
            loadState = false;
            //loads from file
            System.out.println("Runde " + currentRound);
        }
        else {
            field.setField(numberCardsDeck);


            data.field = field;
            ResourceManager.save(data, "gamestate.save");

            logger.info("Field set\n");
            logger.info(field.logField());

            if (currentRound == 1) {
                pc.next();  // initialize current player in PlayerController if it's the first round

            }

            System.out.println("Runde " + currentRound);

            // if we are not in round 1, then we can trade
            if (currentRound >= 2 && !luckyCardDeck.isEmpty()) {
                for (int i = 0; i < pc.getPlayers().size(); i++) {

                    System.out.println("Spieler: " + pc.getCurrentPlayer().getName() + "\nKarte gegen Glückskarte eintauschen? [y,yes,ja | n,no,nein]");


                    if ((pc.getCurrentPlayer().isHuman() && safeScanner.nextYesNoAnswer()) || (!pc.getCurrentPlayer().isHuman() && (((AutonomousPlayer) pc.getCurrentPlayer()).considerPickLuckyCard()))) {

                        // These two lines are only here for cosmetic reasons
                        // to bring the human player a better game experience
                        // by pretending that the bot can also write to the console.
                        if (!pc.getCurrentPlayer().isHuman()) System.out.println("yes");

                        tradeForLucky();
                        pc.getCurrentPlayer().getLuckyCardHand().print();

                    } else {
                        // These two lines are only here for cosmetic reasons
                        // to bring the human player a better game experience
                        // by pretending that the bot can also write to the console.
                        if (!pc.getCurrentPlayer().isHuman()) System.out.println("no");
                    }
                    pc.next();
                }

            }
        }

        pickCardsPhase();

        discardSameColor();
        pc.printPlayerHands();
        discard();
    }

    /**
     * method for user to pick from available cards
     */
    private void pickCardsPhase() throws IllegalAccessException {

        while (true) {

            Player currentPlayer = pc.getCurrentPlayer();

            if (!currentPlayer.isHuman())
                // dont change pc.getCurrentPlayer() to currentPlayer
                ((AutonomousPlayer) pc.getCurrentPlayer()).updateWeightOfNumberCards();

            field.printField();

            System.out.println("\nAktiver Spieler: " + currentPlayer.getName());

            int diceRollResult = throwDice();
            int unchangedResult = diceRollResult;

            if (currentPlayer.getLuckyCardHand().has(LuckyCardNames.LCSum)) {
                System.out.println("Glückskarte Summe benutzen?");
                if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCSum())) {
                    // These two lines are only here for cosmetic reasons
                    // to bring the human player a better game experience
                    // by pretending that the bot can also write to the console.
                    if (!currentPlayer.isHuman()) {
                        System.out.println("yes");
                    }


                    int cardCount = currentPlayer.getLuckyCardHand().count(LuckyCardNames.LCSum);

                    if (cardCount >= 2 && !currentPlayer.isHuman()) {
                        System.out.println("Karte um 1 erhöhen oder reduzieren?");
                        if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer())) {
                            System.out.println("[1] = erhöhen || [2] = reduzieren");
                            int choose = safeScanner.nextIntInRange(1, 2);
                            if (choose == 1) {
                                diceRollResult++;

                            }

                            if (choose == 2) {
                                if (diceRollResult > 2) {
                                    diceRollResult--;
                                }
                            }
                        }
                    }
                    HashSet<List<NumberCard>> hashedCards = new HashSet<>(getCardCombinations(Arrays.stream(field.getField()).toList(), diceRollResult, new ArrayList<>(), new ArrayList<>()));

                    hashedCards.removeIf(list -> list.size() == 1);

                    HashSet<List<NumberCard>> removeDiffColor = new HashSet<>();

                    //store all lists with distinct color cards
                    for (List<NumberCard> list : hashedCards) {
                        for (int i = 0; i < list.size() - 1; i++) {
                            if (list.get(i).getColor() != list.get(i + 1).getColor()) {
                                removeDiffColor.add(list);
                            }
                        }
                    }

                    //remove cards with distinct color from main-list
                    hashedCards.removeAll(removeDiffColor);

                    if (!hashedCards.isEmpty()) {
                        useLCSum(hashedCards);
                        continue;
                    } else {
                        System.out.println("Keine Summe möglich!");
                        diceRollResult = unchangedResult;
                    }
                } else {
                    // These two lines are only here for cosmetic reasons
                    // to bring the human player a better game experience
                    // by pretending that the bot can also write to the console.
                    if (!currentPlayer.isHuman()) {
                        System.out.println("no");
                    }
                }
            }

            List<NumberCard> availableCards = field.getAvailableNumberCards(diceRollResult);

            // if true, then the round is over
            if (availableCards.isEmpty()) {
                System.out.println("Die Runde ist zu ende");
                logger.info("Runde beendet\n");
                // if currentPlayer is a bot, then update NumberCard weights
                if (!currentPlayer.isHuman())
                    // dont change pc.getCurrentPlayer() to currentPlayer
                    ((AutonomousPlayer) pc.getCurrentPlayer()).updateWeightOfNumberCards();
                break;
            }
            // show player available cards
            field.printAvailableCards(availableCards);

            System.out.println("---------------");
            // choose a card
            System.out.println("Wählen sie eine Karte aus: ");

            if (currentPlayer.isHuman()) {
                System.out.println("Wollen sie ein Tipp kriegen?");

                if (safeScanner.nextYesNoAnswer()) {
                    logger.info(currentPlayer.getName() + " hat einen Tipp bekommen\n");
                    AutonomousPlayer autonomousPlayer = new AutonomousPlayer("Tipp Geber", AgentDifficulty.HARD);
                    NumberCard card = autonomousPlayer.givePlayerTip(currentPlayer, availableCards);
                    System.out.println("Ich würde ja diese Karte nehmen:");
                    NumberCard.printFormatedNumberCards(List.of(new NumberCard[]{card}));
                }
            }


            int wantedCardIndex;

            if (currentPlayer.isHuman())
                wantedCardIndex = safeScanner.nextIntInRange(1, availableCards.size()) - 1; // -1 bc for better ux
            else {
                wantedCardIndex = ((AutonomousPlayer) currentPlayer).getIndexOfBestCard(availableCards);
                // This line is only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                System.out.println(wantedCardIndex);
                System.out.println("Begründung für diese Karte: \n" + ((AutonomousPlayer) pc.getCurrentPlayer()).getReasonForCard(availableCards.get(wantedCardIndex)));
            }

            // add card to hand
            NumberCard card = availableCards.get(wantedCardIndex);
            currentPlayer.getNumberCardHand().add(card);
            logger.info(currentPlayer.getName() + " hat Karte: " + card.getName() + " " + card.getColor() + " gewaehlt\n");

            // serializes player hand
            data.map.put(pc.getCurrentPlayer(), pc.getCurrentPlayer().getNumberCardHand());

            System.out.println("Spieler: " + currentPlayer.getName());
            currentPlayer.getNumberCardHand().print();
            System.out.println("---------------");
            // remove card that the player chose from field
            field.removeChosenCard(card);

            // serializes field
            data.field = field;
            ResourceManager.save(data, "gamestate.save");


            logger.info("\n" + field.logField());

            // switch to next player
            pc.next();

            // if currentPlayer is a bot, then update NumberCard weights
            if (!pc.getCurrentPlayer().isHuman())
                // dont change pc.getCurrentPlayer() to currentPlayer
                ((AutonomousPlayer) pc.getCurrentPlayer()).updateWeightOfNumberCards();
        }
    }

    /**
     * user prompt to throw dice
     *
     * @return user chosen dice value
     */
    private int throwDice() throws IllegalAccessException {

        Player currentPlayer = pc.getCurrentPlayer();
        data.currentPlayer = currentPlayer;
        ResourceManager.save(data,"gamestate.save");

        Stack<Integer> diceStack = new Stack<>();

        if (currentPlayer.getLuckyCardHand().has(LuckyCardNames.LC123) || currentPlayer.getLuckyCardHand().has(LuckyCardNames.LC456)) {
            System.out.println("Glückskarte 123 oder 456 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && (((AutonomousPlayer) currentPlayer).considerUseOfLC123() || ((AutonomousPlayer) currentPlayer).considerUseOfLC456()))) {

                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("yes");

                return use123or456();
            }

            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!currentPlayer.isHuman()) System.out.println("no");
        }

        if (currentPlayer.isHuman()) {
            System.out.println("Drücken sie eine Taste um zu Würfeln");

            safeScanner.nextStringSafe();
        }

        diceStack.push(dice.use());

        System.out.println("Du hast eine " + diceStack.peek() + " gewürfelt\nNochmal wuerfeln? [yes|no]");
        logger.info("\n" + currentPlayer.getName() + " hat eine: " + diceStack.peek() + " gewuerfelt\n");

        if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerRollDiceAgain(diceStack))) {
            logger.info(currentPlayer.getName() + " hat nochmal wuerfeln ausgewaehlt\n");
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!currentPlayer.isHuman()) System.out.println("yes");

            diceStack.push(dice.use());
            System.out.println("Du hast eine " + diceStack.peek() + " gewürfelt");
            logger.info(currentPlayer.getName() + " hat eine: " + diceStack.peek() + " gewuerfelt\n");
        } else {
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!currentPlayer.isHuman()) System.out.println("no");
        }


        if (currentPlayer.getLuckyCardHand().has(LuckyCardNames.LCPlusDicethrow)) {
            System.out.println("Glückskarte benutzen um nochmal zu Würfeln:");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCPlusDiceThrow(diceStack))) {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("yes");

                diceStack.push(useReroll());
            } else {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("no");
            }
        }

        if (currentPlayer.getLuckyCardHand().has(LuckyCardNames.LC123) || currentPlayer.getLuckyCardHand().has(LuckyCardNames.LC456)) {
            System.out.println("Glückskarte 123 oder 456 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && (((AutonomousPlayer) currentPlayer).considerUseOfLC123() || ((AutonomousPlayer) currentPlayer).considerUseOfLC456()))) {

                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("yes");

                return use123or456();
            } else {

                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("no");
            }
        }

        if (diceStack.size() > 1) {
            useUndo(diceStack);
        }


        if (pc.getCurrentPlayer().getLuckyCardHand().has(LuckyCardNames.LCPlus1)) {
            System.out.println("Glückskarte Plus 1 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCPlus1(diceStack.peek()))) {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("yes");
                diceStack.push(usePlus(diceStack.peek()));
            } else {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("no");
            }
        }

        if (diceStack.size() > 1) {
            useUndo(diceStack);
        }

        if (pc.getCurrentPlayer().getLuckyCardHand().has(LuckyCardNames.LCMinus1)) {
            System.out.println("Glückskarte Minus 1 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) || (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCMinus1(diceStack.peek()))) {
                diceStack.push(useMinus(diceStack.peek()));
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("yes");
            } else {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman()) System.out.println("no");
            }
        }

        if (diceStack.size() > 1) {
            useUndo(diceStack);
        }

        return diceStack.peek();
    }

    private void useUndo(Stack<Integer> diceStack) {
        System.out.println("Wollen sie die undo Funktion nutzen um ein vorheriges Würfelergebnis zurück zu holen?");
        if ((pc.getCurrentPlayer().isHuman() && safeScanner.nextYesNoAnswer()) || (!pc.getCurrentPlayer().isHuman() && ((AutonomousPlayer) pc.getCurrentPlayer()).considerUseOfUndo(diceStack))) {
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!pc.getCurrentPlayer().isHuman()) System.out.println("yes");

            logger.info(pc.getCurrentPlayer().getName() + " hat undo benutzt\n");

            pc.getCurrentPlayer().setUsedCheats(true);

            while (diceStack.size() > 1) {
                diceStack.pop();
                System.out.println("Ihr Würfelergebnis ist nun " + diceStack.peek());
                logger.info(pc.getCurrentPlayer().getName() + " hat altes Wuerfelergebnis: " + diceStack.peek() + "\n");
                System.out.println("Nochmal undo nutzen ?");
                if (!(pc.getCurrentPlayer().isHuman() && safeScanner.nextYesNoAnswer()) || !(!pc.getCurrentPlayer().isHuman() && ((AutonomousPlayer) pc.getCurrentPlayer()).considerUseOfUndo(diceStack))) {
                    // These two lines are only here for cosmetic reasons
                    // to bring the human player a better game experience
                    // by pretending that the bot can also write to the console.
                    if (!pc.getCurrentPlayer().isHuman()) System.out.println("no");
                    break;
                }
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!pc.getCurrentPlayer().isHuman()) System.out.println("yes");
            }
        } else {
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!pc.getCurrentPlayer().isHuman()) System.out.println("no");
        }
    }


    /**
     * trade Numbercards for luckycards after beginning of 2nd round
     */
    private void tradeForLucky() {

        if (pc.getCurrentPlayer().getNumberCardHand().isEmpty()) {
            System.out.println("Du hast leider keine Karten mehr auf der Hand");
            return;
        }
        pc.getCurrentPlayer().getNumberCardHand().print();

        System.out.println("Welche Karte wollen sie eintauschen?");
        // Get index of card we want to trade for a lucky card
        int index;
        if (pc.getCurrentPlayer().isHuman()) {
            index = safeScanner.nextIntInRange(1, pc.getCurrentPlayer().getNumberCardHand().size()) - 1;
        } else {
            // AI uses the baddest card in his hand
            index = ((AutonomousPlayer) pc.getCurrentPlayer()).findCardForTrade();
            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println(index);
        }

        logger.info(pc.getCurrentPlayer().getName() + " hat: " + pc.getCurrentPlayer().getNumberCardHand().get(index).getName() + " "
                + pc.getCurrentPlayer().getNumberCardHand().get(index).getColor() + " gegen eine Luckycard getauscht \n");
        pc.getCurrentPlayer().getNumberCardHand().remove(index);

        LuckyCard pickedLuckyCard = luckyCardDeck.pop();

        data.luckyDeck = luckyCardDeck;
        ResourceManager.save(data,"gamestate.save");

        System.out.println("Sie haben die LuckyCard " + pickedLuckyCard.getName() + " gezogen");
        logger.info(pc.getCurrentPlayer().getName() + " hat: " + pickedLuckyCard.getName() + " gezogen\n");

        LuckyCard.printFormatedLuckyCards(new ArrayList<>(List.of(new LuckyCard[]{pickedLuckyCard})));

        pc.getCurrentPlayer().getLuckyCardHand().add(pickedLuckyCard);

        if (!luckyCardDeck.isEmpty()) {
            System.out.println("Wollen sie noch eine Karte eintauschen?");

            if ((pc.getCurrentPlayer().isHuman() && safeScanner.nextYesNoAnswer()) || (!pc.getCurrentPlayer().isHuman() && ((AutonomousPlayer) pc.getCurrentPlayer()).considerPickLuckyCard())) {
                tradeForLucky();
            }
        }
    }

    /**
     * Methods calculates all possible combinations of number cards for a specific target
     *
     * @param field   current field
     * @param wuerfel dice result
     * @param partial partial stored cards
     * @param result  all sums
     * @return list with all combinations
     */
    private List<List<NumberCard>> getCardCombinations(List<NumberCard> field, int wuerfel, List<NumberCard> partial, List<List<NumberCard>> result) {

        int sum = 0;
        for (NumberCard x : partial) {
            if (x != null) {
                sum += Integer.parseInt(x.getName());
            }
        }
        if (sum == wuerfel) {
            result.add(partial);
        }
        if (sum >= wuerfel) {
            return result;
        }

        for (int i = 0; i < field.size(); i++) {
            ArrayList<NumberCard> remaining = new ArrayList<>();

            for (int j = i + 1; j < field.size(); j++) {
                if (field.get(j) != null) {
                    remaining.add(field.get(j));
                }
            }

            ArrayList<NumberCard> partial_rec = new ArrayList<>(partial);
            if (field.get(i) != null) {
                partial_rec.add(field.get(i));
            }
            getCardCombinations(remaining, wuerfel, partial_rec, result);
        }

        return result;
    }


    /**
     * choose a sum in relation to your dice throw
     */
    private void useLCSum(HashSet<List<NumberCard>> set) {
        logger.info(pc.getCurrentPlayer().getName() + " hat LCSum benutzt");
        //List out of set for indexing
        List<List<NumberCard>> combinations = new ArrayList<>(set);

        //print cards
        for (List<NumberCard> list : combinations) {
            NumberCard.printFormatedNumberCards(list);
        }

        System.out.println("---------------");
        // choose a card
        System.out.println("Wählen sie ein Kartenpaar aus: ");

        int wantedCardIndex;

        if (pc.getCurrentPlayer().isHuman())
            wantedCardIndex = safeScanner.nextIntInRange(1, combinations.size()) - 1; // -1 bc for better ux
        else {
            wantedCardIndex = ((AutonomousPlayer) pc.getCurrentPlayer()).getIndexOfBestCardCombination(combinations);
            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println(wantedCardIndex);
        }
        pc.getCurrentPlayer().getNumberCardHand().addAll(combinations.get(wantedCardIndex));

        for (NumberCard card : combinations.get(wantedCardIndex)) {
            logger.info(pc.getCurrentPlayer().getName() + " hat: " + card.getName() + " " + card.getColor() + " bekommen\n");
        }

        System.out.println("Spieler: " + pc.getCurrentPlayer().getName());
        pc.getCurrentPlayer().getNumberCardHand().print();
        System.out.println("---------------");

        //removes cards from field
        for (int i = 0; i < field.getFieldSize(); i++) {
            for (int j = 0; j < combinations.get(wantedCardIndex).size(); j++) {
                if (field.getField()[i] == combinations.get(wantedCardIndex).get(j)) {
                    field.removeChosenCard(combinations.get(wantedCardIndex).get(j));
                }
            }
        }
        // switch to next player
        pc.next();
    }


    /**
     * choose a card with number 123 or 456 from field if use is available
     *
     * @return player chosen number
     */
    private int use123or456() throws IllegalAccessException {
        pc.getCurrentPlayer().getLuckyCardHand().print();

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            if (((AutonomousPlayer) pc.getCurrentPlayer()).considerUseOfLC123()) {
                for (LuckyCard card : pc.getCurrentPlayer().getLuckyCardHand()) {
                    if (card.getName().equals(LuckyCardNames.LC123.name()))
                        index = pc.getCurrentPlayer().getLuckyCardHand().indexOf(card);
                }
            } else {
                for (LuckyCard card : pc.getCurrentPlayer().getLuckyCardHand()) {
                    if (card.getName().equals(LuckyCardNames.LC456.name()))
                        index = pc.getCurrentPlayer().getLuckyCardHand().indexOf(card);
                }
            }

            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println("Welche Karte wollen sie verwenden: \n" + index);
        }

        if (pc.getCurrentPlayer().getLuckyCardHand().get(index).getName().equals(LuckyCardNames.LC123.name()) || pc.getCurrentPlayer().getLuckyCardHand().get(index).getName().equals(LuckyCardNames.LC456.name())) {

            logger.info(pc.getCurrentPlayer().getName() + " hat: " + pc.getCurrentPlayer().getLuckyCardHand().get(index).getName() + " benutzt\n");
            int diceValue = pc.getCurrentPlayer().getLuckyCardHand().get(index).effect();

            logger.info("Neues Wuerfelergebnis ist: " + diceValue + "\n");
            logger.info(pc.getCurrentPlayer().getLuckyCardHand().get(index).getName() + " wurde aus: " + pc.getCurrentPlayer().getName() + "s Hand entfernt\n");

            pc.getCurrentPlayer().getLuckyCardHand().remove(index);
            return diceValue;
        } else {
            return use123or456();
        }
    }

    /**
     * adds 1 to dicevalue
     *
     * @param dice rolled dice
     * @return dicevalue + 1
     */
    private int usePlus(int dice) throws IllegalAccessException {
        pc.getCurrentPlayer().getLuckyCardHand().print();

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            for (LuckyCard card : pc.getCurrentPlayer().getLuckyCardHand()) {
                if (card.getName().equals(LuckyCardNames.LCPlus1.name()))
                    index = pc.getCurrentPlayer().getLuckyCardHand().indexOf(card);
            }

            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println("Welche Karte wollen sie verwenden: \n" + index);
        }

        if (pc.getCurrentPlayer().getLuckyCardHand().get(index).getName().equals(LuckyCardNames.LCPlus1.name())) {

            logger.info(pc.getCurrentPlayer().getName() + " hat LCPlus1 benutzt\n");
            int value = pc.getCurrentPlayer().getLuckyCardHand().get(index).effect() + dice;

            if (value > 6) {
                value = 6;
            }
            logger.info("Neues Wuerfelergebnis: " + value + "\n");
            return value;
        } else {
            return usePlus(dice);
        }
    }

    /**
     * subtracts 1 from dicevalue
     *
     * @param dice rolled dice
     * @return dicevalue - 1
     */
    private int useMinus(int dice) throws IllegalAccessException {
        pc.getCurrentPlayer().getLuckyCardHand().print();

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            for (LuckyCard card : pc.getCurrentPlayer().getLuckyCardHand()) {
                if (card.getName().equals(LuckyCardNames.LCMinus1.name()))
                    index = pc.getCurrentPlayer().getLuckyCardHand().indexOf(card);
            }

            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println("Welche Karte wollen sie verwenden: \n" + index);
        }

        if (pc.getCurrentPlayer().getLuckyCardHand().get(index).getName().equals(LuckyCardNames.LCMinus1.name())) {

            logger.info(pc.getCurrentPlayer().getName() + " hat LCMinus1 benutzt\n");
            int value = pc.getCurrentPlayer().getLuckyCardHand().get(index).effect() + dice;

            if (value < 1) {
                value = 1;
            }
            logger.info("Neues Wuerfelergebnis: " + value + "\n");
            return value;
        } else {
            return useMinus(dice);
        }
    }

    /**
     * allows player to reroll with luckycard
     *
     * @return new rolled dice value
     */
    private int useReroll() throws IllegalAccessException {
        pc.getCurrentPlayer().getLuckyCardHand().print();

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            for (LuckyCard card : pc.getCurrentPlayer().getLuckyCardHand()) {
                if (card.getName().equals(LuckyCardNames.LCPlusDicethrow.name()))
                    index = pc.getCurrentPlayer().getLuckyCardHand().indexOf(card);
            }

            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println("Welche Karte wollen sie verwenden: \n" + index);
        }

        if (pc.getCurrentPlayer().getLuckyCardHand().get(index).getName().equals(LuckyCardNames.LCPlusDicethrow.name())) {
            logger.info(pc.getCurrentPlayer().getName() + " hat useReroll benutzt\n");
            return pc.getCurrentPlayer().getLuckyCardHand().get(index).effect();
        }
        return useReroll();
    }

    /**
     * Asks the player the index of the LuckyCard in their hand
     *
     * @return returns the selected index
     */
    private int selectLuckyCardIndex() {
        System.out.println("Welche Karte wollen sie verwenden: ");
        return safeScanner.nextIntInRange(1, pc.getCurrentPlayer().getLuckyCardHand().size());
    }

    /**
     * discards cards of same color at end of round
     */
    private void discardSameColor() {
        for (Player player : pc.getPlayers()) {

            List<NumberCard> tempCards = new ArrayList<>();

            for (NumberCard cardsOfPlayer : player.getNumberCardHand()) {

                for (NumberCard cardInField : field.getField()) {
                    if (cardInField != null && cardsOfPlayer.getColor().equals(cardInField.getColor())) {
                        tempCards.add(cardsOfPlayer);
                        break;
                    }
                }
            }

            // show which cards are removed
            player.getNumberCardHand().removeAll(tempCards);
        }
    }

    /**
     * Finds the highest NumberCard in the hand of the player
     */
    private List<NumberCard> findHighest() {
        List<NumberCard> highestCards = new ArrayList<>();

        Player currentPlayer = pc.getCurrentPlayer();

        // check if player has no number cards
        if (currentPlayer.getNumberCardHand().isEmpty()) {
            return highestCards;
        }

        // find the highest number card
        NumberCard max = currentPlayer.getNumberCardHand().get(0);

        for (NumberCard card : currentPlayer.getNumberCardHand()) {

            if (Integer.parseInt(card.getName()) > Integer.parseInt(max.getName())) {
                max = card;
            }
        }

        highestCards.add(max);

        for (NumberCard card : currentPlayer.getNumberCardHand()) {
            if (card.getName().equals(max.getName()) && !card.equals(max)) {
                highestCards.add(card);
            }
        }

        return highestCards;
    }

    /**
     * discards highest NumberCard from playerhand
     */
    private void discard() {
        if (pc.getCurrentPlayer().getNumberCardHand().isEmpty()) {
            return;
        }
        List<NumberCard> highest = findHighest();

        NumberCard.printFormatedNumberCards(highest);

        System.out.println("Welche Karte möchtest du wegwerfen?");
        int index;

        if (pc.getCurrentPlayer().isHuman()) {
            index = safeScanner.nextIntInRange(1, highest.size()) - 1;
        } else {
            index = ((AutonomousPlayer) pc.getCurrentPlayer()).findIndexForTheBestCardToDiscard(highest);
            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println(index);
        }

        //remove the highest from current player that ended turn
        logger.info(pc.getCurrentPlayer().getName() + " hat: " + pc.getCurrentPlayer().getNumberCardHand().get(index).getName() + " " +
                pc.getCurrentPlayer().getNumberCardHand().get(index).getColor() + " weggeworfen\n");
        pc.getCurrentPlayer().getNumberCardHand().remove(index);

        System.out.println("NACH WEGWURF ----------------");
        pc.printPlayerHands();
    }

    public FileHandler getFh(){
        return fh;
    }

}
