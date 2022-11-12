package org.jinx.game;

import org.jinx.card.LCSum;
import org.jinx.card.LuckyCard;
import org.jinx.card.LuckyCardNames;
import org.jinx.card.NumberCard;
import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.dice.Dice;
import org.jinx.field.Field;
import org.jinx.player.Player;
import org.jinx.wrapper.SafeScanner;

import java.util.*;


public class Game {

    private final PlayerController pc = PlayerController.getPlayerControllerInstance();

    private final Dice dice;
    private final NumberCardStack numberCardsDeck;
    private final LuckyCardStack luckyCardStack;

    private final Field field;

    private final SafeScanner safeScanner;


    public Game() {
        dice = new Dice();

        numberCardsDeck = new NumberCardStack();

        luckyCardStack = new LuckyCardStack();

        field = new Field();

        safeScanner = new SafeScanner();

    }

    /**
     * This method controls the gameflow for each round
     */
    public void play(int currentRound) {

        // initialize current player in PlayerController
        if (currentRound == 1) {
            pc.next();
        }

        // Lay new cards on field to replace old field
        field.setField(numberCardsDeck);

        System.out.println("Runde " + currentRound);

        // if we are not in round 1, then we can trade
        if (currentRound >= 2) {
            for (int i = 0; i < pc.getPlayers().size(); i++) {

                System.out.println("Spieler: " + pc.getCurrentPlayer().getName() +
                        "\nKarte gegen Glückskarte eintauschen? [y,yes,ja | n,no,nein]");

                boolean doTrade = safeScanner.nextYesNoAnswer();
                if (doTrade) {
                    tradeForLucky();
                    pc.getCurrentPlayer().printLuckyHand();
                }
                pc.next();
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
    private void pickCardsPhase() {

        while (true) {
            field.printField();

            System.out.println("\nAktiver Spieler: " + pc.getCurrentPlayer().getName());

            int diceRollResult = throwDice();
            int unchangedResult = diceRollResult;


            if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LCSum)) {
                System.out.println("Glückskarte Summe benutzen?");
                if (safeScanner.nextYesNoAnswer()) {

                    int cardCount = pc.getCurrentPlayer().countLuckyCards(LuckyCardNames.LCSum);

                    if (cardCount >= 2) {
                        System.out.println("Karte um 1 erhöhen oder reduzieren?");
                        if (safeScanner.nextYesNoAnswer()) {
                            System.out.println("[1] = erhöhen || [2] = reduzieren");
                            int choose = safeScanner.nextIntInRange(1, 2);
                            if (choose == 1) {
                                diceRollResult++;

                            }

                            if (choose == 2) {
                                if (diceRollResult != 1) {
                                    diceRollResult--;
                                }
                            }
                        }
                    }
                    HashSet<List<NumberCard>> hashedCards = new HashSet<>(useLCSUMrecursive(Arrays.stream(field.getField()).toList(), diceRollResult, new ArrayList<>(), new ArrayList<>()));

                    hashedCards.removeIf(list -> list.size() == 1);

                    if(!hashedCards.isEmpty()){
                        useLCSUM(hashedCards);
                        continue;
                    }

                    else {
                        System.out.println("Keine Summe möglich!");
                        diceRollResult = unchangedResult;
                    }
                }
            }

            List<NumberCard> availableCards = field.getAvailableNumberCards(diceRollResult);

            // if true, then the round is over
            if (availableCards.isEmpty()) {
                System.out.println("Die Runde ist zu ende");
                break;
            }
            // show player available cards
            field.printAvailableCards(availableCards);

            System.out.println("---------------");
            // choose a card
            System.out.println("Wählen sie eine Karte aus: ");

            int wantedCard = safeScanner.nextIntInRange(1, availableCards.size()); // +1 bc for better ux

            // add card to hand
            NumberCard card = availableCards.get(wantedCard - 1);
            pc.getCurrentPlayer().getCards().add(card);

            System.out.println("Spieler: " + pc.getCurrentPlayer().getName());
            pc.getCurrentPlayer().printHand();
            System.out.println("---------------");
            // remove card that the player chose from field
            field.removeChosenCard(card);

            // switch to next player
            pc.next();
        }
    }

    /**
     * user prompt to throw dice
     *
     * @return user chosen dice value
     */
    private int throwDice() {

        pc.getCurrentPlayer().getLuckyCards().add(new LCSum());
        pc.getCurrentPlayer().getLuckyCards().add(new LCSum());

        Stack<Integer> diceStack = new Stack<>();

        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LC123) || pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LC456)) {
            System.out.println("Glückskarte 123 oder 456 benutzen?");
            if (safeScanner.nextYesNoAnswer()) {
                return use123or456();
            }
        }

        System.out.println("Drücken sie eine Taste um zu Würfeln");

        safeScanner.nextStringSafe();

        diceStack.push(dice.use());

        System.out.println("Du hast eine " + diceStack.peek() +
                " gewürfelt\nNochmal wuerfeln? [yes|no]");

        if (safeScanner.nextYesNoAnswer()) {
            diceStack.push(dice.use());
            System.out.println("Du hast eine " + diceStack.peek() + " gewürfelt");
        }

        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LCPlusDicethrow)) {
            System.out.println("Glückskarte benutzen um nochmal zu Würfeln:");
            if (safeScanner.nextYesNoAnswer()) {
                diceStack.push(useReroll());
            }
        }

        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LC123) || pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LC456)) {
            System.out.println("Glückskarte 123 oder 456 benutzen?");
            if (safeScanner.nextYesNoAnswer()) {
                return use123or456();
            }
        }

        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LCPlus1)) {
            System.out.println("Glückskarte Plus 1 benutzen?");
            if (safeScanner.nextYesNoAnswer()) {
                diceStack.push(usePlus(diceStack.peek()));
            }
        }

        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LCMinus1)) {
            System.out.println("Glückskarte Minus 1 benutzen?");
            if (safeScanner.nextYesNoAnswer()) {
                diceStack.push(useMinus(diceStack.peek()));
            }
        }

        if (diceStack.size() > 1) {
            useUndo(diceStack);
        }

        return diceStack.peek();
    }

    private void useUndo(Stack<Integer> diceStack) {
        System.out.println("Wollen sie die undo Funktion nutzen um ein vorheriges Würfelergebnis zurück zu holen?");
        if (safeScanner.nextYesNoAnswer()) {

            pc.getCurrentPlayer().setUsedRedo(true);

            while (diceStack.size() > 1) {
                diceStack.pop();
                System.out.println("Ihr Würfelergebnis ist nun " + diceStack.peek());

                System.out.println("Nochmal undo nutzen ?");
                if (!safeScanner.nextYesNoAnswer()) {
                    break;
                }
            }
        }
    }


    /**
     * trade Numbercards for luckycards after beginning of 2nd round
     */
    private void tradeForLucky() {

        if (pc.getCurrentPlayer().getCards().isEmpty()) {
            System.out.println("Du hast leider keine Karten mehr auf der Hand");
            return;
        }
        pc.getCurrentPlayer().printHand();

        System.out.println("Welche Karte wollen sie eintauschen?");
        // Get index of card we want to trade for a lucky card
        int index = safeScanner.nextIntInRange(1, pc.getCurrentPlayer().getCards().size());

        pc.getCurrentPlayer().getCards().remove(index - 1);

        pc.getCurrentPlayer().getLuckyCards().add(luckyCardStack.pop());

        System.out.println("Wollen sie noch eine Karte eintauschen?");

        if (safeScanner.nextYesNoAnswer()) {
            tradeForLucky();
        }
    }

    /**
     * recurse function to calculate sum of multiple numbers
     *
     * @param field   current field
     * @param wuerfel dice result
     * @param partial partial stored cards
     * @param result  all sums
     * @return list with all sums
     */
    private List<List<NumberCard>> useLCSUMrecursive(List<NumberCard> field, int wuerfel, List<NumberCard> partial, List<List<NumberCard>> result) {

        int s = 0;
        for (NumberCard x : partial) {
            if (x != null) {
                s += Integer.parseInt(x.getName());
            }
        }
        if (s == wuerfel) {
            result.add(partial);
        }
        if (s >= wuerfel) {
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
            useLCSUMrecursive(remaining, wuerfel, partial_rec, result);
        }

        return result;
    }


    /**
     * choose a sum in relation to your dice throw
     */
    private void useLCSUM(HashSet<List<NumberCard>> set) {


        //List out of set for indexing
        List<List<NumberCard>> newCards = new ArrayList<>(set);


        //print cards
        for (List<NumberCard> list : newCards) {
            printCards(list);
        }

        System.out.println("---------------");
        // choose a card
        System.out.println("Wählen sie ein Kartenpaar aus: ");

        //adds cards to player hand
        int index = safeScanner.nextIntInRange(1, newCards.size()) - 1;
        pc.getCurrentPlayer().getCards().addAll(newCards.get(index));

        System.out.println("Spieler: " + pc.getCurrentPlayer().getName());
        pc.getCurrentPlayer().printHand();
        System.out.println("---------------");

        //removes cards from field
        for (int i = 0; i < field.getFieldSize(); i++) {
            for (int j = 0; j < newCards.get(index).size(); j++) {
                if (field.getField()[i] == newCards.get(index).get(j)) {
                    field.removeChosenCard(newCards.get(index).get(j));
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
    private int use123or456() {
        pc.getCurrentPlayer().printLuckyHand();

        int index = selectLuckyCardIndex();

        if (pc.getCurrentPlayer().getLuckyCards().get(index - 1).getName().equals("LC123")
                || pc.getCurrentPlayer().getLuckyCards().get(index - 1).getName().equals("LC456")) {

            int diceValue = pc.getCurrentPlayer().getLuckyCards().get(index - 1).effect();

            pc.getCurrentPlayer().getLuckyCards().remove(index - 1);

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
    private int usePlus(int dice) {

        pc.getCurrentPlayer().printLuckyHand();

        int index = selectLuckyCardIndex();

        if (pc.getCurrentPlayer().getLuckyCards().get(index - 1).getName().equals("LCPlus1")) {
            int value = pc.getCurrentPlayer().getLuckyCards().get(index - 1).effect() + dice;

            if (value >= 6) {
                value = 6;
            }

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
    private int useMinus(int dice) {
        pc.getCurrentPlayer().printLuckyHand();

        int index = selectLuckyCardIndex();

        if (pc.getCurrentPlayer().getLuckyCards().get(index - 1).getName().equals("LCMinus1")) {
            int value = pc.getCurrentPlayer().getLuckyCards().get(index - 1).effect() + dice;

            if (value <= 1) {
                value = 1;
            }

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
    private int useReroll() {
        pc.getCurrentPlayer().printLuckyHand();

        int index = selectLuckyCardIndex();

        if (pc.getCurrentPlayer().getLuckyCards().get(index - 1).getName().equals("LCPlusDicethrow")) {
            return pc.getCurrentPlayer().getLuckyCards().get(index - 1).effect();
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
        return safeScanner.nextIntInRange(1, pc.getCurrentPlayer().getLuckyCards().size());
    }

    /**
     * discards cards of same color at end of round
     */
    private void discardSameColor() {
        for (Player player : pc.getPlayers()) {

            List<NumberCard> tempCards = new ArrayList<>();

            for (NumberCard cardsOfPlayer : player.getCards()) {

                for (NumberCard cardInField : field.getField()) {
                    if (cardInField != null && cardsOfPlayer.getColor().equals(cardInField.getColor())) {
                        tempCards.add(cardsOfPlayer);
                        break;
                    }
                }
            }

            // show which cards are removed
            player.getCards().removeAll(tempCards);
        }
    }

    /**
     * Finds the highest NumberCard in the hand of the player
     */
    private List<NumberCard> findHighest() {
        List<NumberCard> highestCards = new ArrayList<>();

        Player currentPlayer = pc.getCurrentPlayer();

        // check if player has no number cards
        if (currentPlayer.getCards().isEmpty()) {
            return highestCards;
        }

        // find the highest number card
        NumberCard max = currentPlayer.getCards().get(0);

        for (NumberCard card : currentPlayer.getCards()) {

            if (Integer.parseInt(card.getName()) > Integer.parseInt(max.getName())) {
                max = card;
            }
        }

        highestCards.add(max);

        for (NumberCard card : currentPlayer.getCards()) {
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
        if (pc.getCurrentPlayer().getCards().isEmpty()) {
            return;
        }
        List<NumberCard> highest = findHighest();
        // scanner for index input

        printCards(highest);

        System.out.println("Welche Karte möchtest du wegwerfen?");

        int index = safeScanner.nextIntInRange(1, highest.size());

        //remove the highest from current player that ended turn
        pc.getCurrentPlayer().getCards().remove(highest.get(index - 1));

        System.out.println("NACH WEGWURF ----------------");
        pc.printPlayerHands();

    }

    private void printCards(List<NumberCard> list) {
        System.out.print("----------\t".repeat(list.size()) + "\n");

        System.out.println("|        |\t".repeat(list.size()));

        // print card number
        for (NumberCard card : list) {
            System.out.print("| " + card.getName() + " ".repeat(6) + "|\t");
        }

        System.out.println();
        System.out.println("|        |\t".repeat(list.size()));

        for (NumberCard card : list) {
            System.out.print("| " + card.getColor() + (card.getColor().name().length() < 6 ? (" ".repeat(6 - card.getColor().name().length())) : "") + " |\t");
        }
        System.out.println();
        System.out.println("|        |\t".repeat(list.size()));
        System.out.print("----------\t".repeat(list.size()) + "\n");
    }

}
