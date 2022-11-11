package org.jinx.game;

import org.jinx.card.*;
import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.dice.Dice;
import org.jinx.field.Field;
import org.jinx.player.AutonomousPlayer;
import org.jinx.player.Player;
import org.jinx.wrapper.SafeScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Game {

    private final PlayerController pc = PlayerController.getPlayerControllerInstance();

    private final Dice dice;
    private final NumberCardStack numberCardsDeck;
    private final LuckyCardStack luckyCardStack;

    private final Field field = Field.getFieldInstance();

    private final SafeScanner safeScanner;

    public Game() {
        dice = new Dice();

        numberCardsDeck = new NumberCardStack();

        luckyCardStack = new LuckyCardStack();


        safeScanner = new SafeScanner();
    }

    /**
     * This method controls the gameflow for each round
     */
    public void play(int currentRound) {


        if (currentRound == 1) {
            pc.next();  // initialize current player in PlayerController if it's the first round

            // if currentPlayer is a bot, then update NumberCard weights
            if (!pc.getCurrentPlayer().isHuman())
                ((AutonomousPlayer) pc.getCurrentPlayer()).updateWeightOfNumberCards();
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

            // if currentPlayer is a bot, then update NumberCard weights
            if (!pc.getCurrentPlayer().isHuman())
                ((AutonomousPlayer) pc.getCurrentPlayer()).updateWeightOfNumberCards();
        }
    }

    /**
     * user prompt to throw dice
     *
     * @return user chosen dice value
     */
    private int throwDice() {


        Player currentPlayer = pc.getCurrentPlayer();
        if (!currentPlayer.isHuman()) {
            currentPlayer.getLuckyCards().add(new LC123());

            currentPlayer.getLuckyCards().add(new LC456());

            currentPlayer.getLuckyCards().add(new LCMinus1());
            currentPlayer.getLuckyCards().add(new LCPlus1());

            currentPlayer.getLuckyCards().add(new LCPlusDicethrow());

        }
        Stack<Integer> diceStack = new Stack<>();

        if (currentPlayer.hasLuckyCard(LuckyCardNames.LC123) || currentPlayer.hasLuckyCard(LuckyCardNames.LC456)) {
            System.out.println("Glückskarte 123 oder 456 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) ||
                    (!currentPlayer.isHuman() &&
                            (((AutonomousPlayer) currentPlayer).considerUseOfLC123() ||
                                    ((AutonomousPlayer) currentPlayer).considerUseOfLC456()))) {

                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("yes");

                return use123or456();
            }

            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!currentPlayer.isHuman())
                System.out.println("no");
        }

        if (currentPlayer.isHuman()) {
            System.out.println("Drücken sie eine Taste um zu Würfeln");

            safeScanner.nextStringSafe();
        }

        diceStack.push(dice.use());

        System.out.println("Du hast eine " + diceStack.peek() +
                " gewürfelt\nNochmal wuerfeln? [yes|no]");

        if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) ||
                (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerRollDiceAgain(diceStack))) {

            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!currentPlayer.isHuman())
                System.out.println("yes");

            diceStack.push(dice.use());
            System.out.println("Du hast eine " + diceStack.peek() + " gewürfelt");
        } else {
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!currentPlayer.isHuman())
                System.out.println("no");
        }


        if (currentPlayer.hasLuckyCard(LuckyCardNames.LCPlusDicethrow)) {
            System.out.println("Glückskarte benutzen um nochmal zu Würfeln:");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) ||
                    (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCPlusDiceThrow(diceStack))) {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("yes");

                diceStack.push(useReroll());
            } else {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("no");
            }
        }

        if (currentPlayer.hasLuckyCard(LuckyCardNames.LC123) || currentPlayer.hasLuckyCard(LuckyCardNames.LC456)) {
            System.out.println("Glückskarte 123 oder 456 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) ||
                    (!currentPlayer.isHuman() &&
                            (((AutonomousPlayer) currentPlayer).considerUseOfLC123() ||
                                    ((AutonomousPlayer) currentPlayer).considerUseOfLC456()))) {

                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("yes");

                return use123or456();
            } else {

                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("no");
            }
        }


        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LCPlus1)) {
            System.out.println("Glückskarte Plus 1 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) ||
                    (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCPlus1(diceStack.peek()))) {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("yes");
                diceStack.push(usePlus(diceStack.peek()));
            } else {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("no");
            }
        }

        if (pc.getCurrentPlayer().hasLuckyCard(LuckyCardNames.LCMinus1)) {
            System.out.println("Glückskarte Minus 1 benutzen?");
            if ((currentPlayer.isHuman() && safeScanner.nextYesNoAnswer()) ||
                    (!currentPlayer.isHuman() && ((AutonomousPlayer) currentPlayer).considerUseOfLCMinus1(diceStack.peek()))) {
                diceStack.push(useMinus(diceStack.peek()));
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("yes");
            } else {
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!currentPlayer.isHuman())
                    System.out.println("no");
            }
        }

        if (diceStack.size() > 1) {
            useUndo(diceStack);
        }

        return diceStack.peek();
    }

    private void useUndo(Stack<Integer> diceStack) {
        System.out.println("Wollen sie die undo Funktion nutzen um ein vorheriges Würfelergebnis zurück zu holen?");
        if ((pc.getCurrentPlayer().isHuman() && safeScanner.nextYesNoAnswer()) ||
                (!pc.getCurrentPlayer().isHuman() &&
                        ((AutonomousPlayer) pc.getCurrentPlayer()).considerUseOfUndo(diceStack))) {
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!pc.getCurrentPlayer().isHuman())
                System.out.println("yes");

            pc.getCurrentPlayer().setUsedRedo(true);

            while (diceStack.size() > 1) {
                diceStack.pop();
                System.out.println("Ihr Würfelergebnis ist nun " + diceStack.peek());

                System.out.println("Nochmal undo nutzen ?");
                if (!(pc.getCurrentPlayer().isHuman() && safeScanner.nextYesNoAnswer()) ||
                        !(!pc.getCurrentPlayer().isHuman() &&
                                ((AutonomousPlayer) pc.getCurrentPlayer()).considerUseOfUndo(diceStack))) {
                    // These two lines are only here for cosmetic reasons
                    // to bring the human player a better game experience
                    // by pretending that the bot can also write to the console.
                    if (!pc.getCurrentPlayer().isHuman())
                        System.out.println("no");
                    break;
                }
                // These two lines are only here for cosmetic reasons
                // to bring the human player a better game experience
                // by pretending that the bot can also write to the console.
                if (!pc.getCurrentPlayer().isHuman())
                    System.out.println("yes");
            }
        } else {
            // These two lines are only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            if (!pc.getCurrentPlayer().isHuman())
                System.out.println("no");
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
     * choose a card with number 123 or 456 from field if use is available
     *
     * @return player chosen number
     */
    private int use123or456() {
        pc.getCurrentPlayer().printLuckyHand();

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            if (((AutonomousPlayer) pc.getCurrentPlayer()).considerUseOfLC123()) {
                for (LuckyCard card : pc.getCurrentPlayer().getLuckyCards()) {
                    if (card.getName().equals(LuckyCardNames.LC123.name()))
                        index = pc.getCurrentPlayer().getLuckyCards().indexOf(card);
                }
            } else {
                for (LuckyCard card : pc.getCurrentPlayer().getLuckyCards()) {
                    if (card.getName().equals(LuckyCardNames.LC456.name()))
                        index = pc.getCurrentPlayer().getLuckyCards().indexOf(card);
                }
            }

            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println("Welche Karte wollen sie verwenden: \n" + index);
        }

        if (pc.getCurrentPlayer().getLuckyCards().get(index).getName().equals(LuckyCardNames.LC123.name())
                || pc.getCurrentPlayer().getLuckyCards().get(index).getName().equals(LuckyCardNames.LC456.name())) {

            int diceValue = pc.getCurrentPlayer().getLuckyCards().get(index).effect();

            pc.getCurrentPlayer().getLuckyCards().remove(index);

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

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            for (LuckyCard card : pc.getCurrentPlayer().getLuckyCards()) {
                if (card.getName().equals(LuckyCardNames.LCPlus1.name()))
                    index = pc.getCurrentPlayer().getLuckyCards().indexOf(card);
            }
        }

        if (pc.getCurrentPlayer().getLuckyCards().get(index).getName().equals(LuckyCardNames.LCPlus1.name())) {
            int value = pc.getCurrentPlayer().getLuckyCards().get(index).effect() + dice;

            if (value > 6) {
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

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            for (LuckyCard card : pc.getCurrentPlayer().getLuckyCards()) {
                if (card.getName().equals(LuckyCardNames.LCMinus1.name()))
                    index = pc.getCurrentPlayer().getLuckyCards().indexOf(card);
            }
        }

        if (pc.getCurrentPlayer().getLuckyCards().get(index).getName().equals(LuckyCardNames.LCMinus1.name())) {
            int value = pc.getCurrentPlayer().getLuckyCards().get(index).effect() + dice;

            if (value < 1) {
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

        int index = 0;

        if (pc.getCurrentPlayer().isHuman()) {
            index = selectLuckyCardIndex() - 1;
        } else {
            for (LuckyCard card : pc.getCurrentPlayer().getLuckyCards()) {
                if (card.getName().equals(LuckyCardNames.LCPlusDicethrow.name()))
                    index = pc.getCurrentPlayer().getLuckyCards().indexOf(card);
            }
        }

        if (pc.getCurrentPlayer().getLuckyCards().get(index).getName().equals(LuckyCardNames.LCPlusDicethrow.name())) {
            return pc.getCurrentPlayer().getLuckyCards().get(index).effect();
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

        NumberCard.printFormatedNumberCards(highest);

        System.out.println("Welche Karte möchtest du wegwerfen?");

        int index = safeScanner.nextIntInRange(1, highest.size());

        //remove the highest from current player that ended turn
        pc.getCurrentPlayer().getCards().remove(highest.get(index - 1));

        System.out.println("NACH WEGWURF ----------------");
        pc.printPlayerHands();

    }

}
