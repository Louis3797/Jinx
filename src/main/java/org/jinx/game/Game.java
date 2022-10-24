package org.jinx.game;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.LuckyCardStack;
import org.jinx.cardstack.NumberCardStack;
import org.jinx.dice.Dice;
import org.jinx.field.Field;
import org.jinx.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {

    private final PlayerController pc = PlayerController.getPlayerControllerInstance();

    private final Dice dice;
    private final NumberCardStack numberCardsDeck;

    private final LuckyCardStack luckyCardsDeck;
    private final Field field;

    public Game() {
        dice = new Dice();

        numberCardsDeck = new NumberCardStack();

        luckyCardsDeck = new LuckyCardStack();

        field = new Field();
    }

    /**
     * trade Numbercards for luckycards
     * after beginning of 2nd round
     */
    private void tradeForLucky() {

        if (!pc.getCurrentPlayer().getCards().isEmpty()) {
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Welche Karte eintauschen?");

                for (NumberCard card : pc.getCurrentPlayer().getCards()) {
                    System.out.println(card.toString());
                }

                int index = scanner.nextInt();

                pc.getCurrentPlayer().getCards().remove(index - 1);
                pc.getCurrentPlayer().getLuckyCards().add(luckyCardsDeck.pop());

                System.out.println("Noch eine eintauschen?");
                if (scanner.next().equals("yes")) {
                    tradeForLucky();
                }
            } catch (IndexOutOfBoundsException e) {
                tradeForLucky();
            }
        } else {
            System.out.println("Keine Karten mehr!");
        }
    }


    /**
     * This method controls the gameflow for each round
     */
    public void play(int currentRound) {
        pickAvailable(currentRound);
        discardSameColor();
        printPlayerHands();
        discard();
    }

    /**
     * user prompt to throw dice
     *
     * @return user chosen dice value
     */
    private int throwDice() {

        Scanner scanner = new Scanner(System.in);

        int result = dice.use();
        System.out.println("Wuerfel: " + result +
                "\nNochmal wuerfeln? [yes|no]");

        if (scanner.next().equals("yes")) {
            result = dice.use();
            System.out.println("Wuerfel: " + result);
        }

        return result;

    }

    /**
     * method for user to pick from available cards
     *
     * @param currentRound round the player is in
     */
    private void pickAvailable(int currentRound) {
        Scanner scanner = new Scanner(System.in);
        field.setField(numberCardsDeck);
        System.out.println("Runde " + currentRound);

        for (int i = 0; i < pc.getPlayers().size(); i++) {
            if (currentRound >= 2) {
                System.out.println("Spieler: " + pc.getCurrentPlayer().getName() + "\nKarte gegen Glückskarte eintauschen?");
                if (scanner.next().equals("yes")) {
                    tradeForLucky();
                    pc.getCurrentPlayer().printLuckyHand();
                }
                pc.next();
            }
        }

        while (true) {

            field.printField();
            pc.next(); // Player ändern

            System.out.println("\nAktiver Spieler: " + pc.getCurrentPlayer().getName());


            System.out.println("Drücken sie eine Taste um zu Würfeln");
            scanner.next();

            List<NumberCard> availableCards = field.getAvailableNumberCards(use123or456orDice());

            // if true, then the round is over
            if (availableCards.isEmpty()) {
                System.out.println("ENDE");
                break;
            }
            // show player available cards
            field.printAvailableCards(availableCards);

            System.out.println("\n---------------\n");

            boolean chosen = false;
            while (!chosen) {
                // choose a card
                System.out.println("Wählen sie eine Karte aus: ");
                int index = scanner.nextInt();

                // check for index exception
                if (index <= 0 || index > availableCards.size()) {
                    System.out.println("Falsche Eingabe");

                } else {
                    // add card to hand
                    NumberCard card = availableCards.get(index - 1);
                    pc.getCurrentPlayer().getCards().add(card);
                    chosen = true;

                    System.out.println("Spieler: " + pc.getCurrentPlayer().getName() + "\n" + pc.getCurrentPlayer().getCards().toString() + "\n");

                    field.removeChosenCard(card);
                }
            }
        }
    }

    private int use123or456orDice() {
        Scanner scanner = new Scanner(System.in);
        int diceValue;

        pc.getCurrentPlayer().printLuckyHand();

        if (!pc.getCurrentPlayer().getLuckyCards().isEmpty()) {
            System.out.println("Eine benutzen?");
            if (scanner.next().equals("yes")) {

                System.out.println("index eingeben: ");
                int index = scanner.nextInt();

                if (pc.getCurrentPlayer().getLuckyCards().get(index - 1).getName().equals("LC456")) {
                    diceValue = pc.getCurrentPlayer().getLuckyCards().get(index - 1).effect();
                    System.out.println("DICEVALUE: " + diceValue);
                    return diceValue;
                }

            } else {
                diceValue = throwDice();
                return diceValue;
            }
        }

        diceValue = throwDice();
        return diceValue;
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
            player.getCards().removeAll(tempCards);
        }
    }

    /**
     * finds highest NumberCard in playerhand
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

        List<NumberCard> highest = findHighest();
        // scanner for index input
        Scanner scanner = new Scanner(System.in);
        System.out.println(highest);

        System.out.println("Welche Karte möchtest du wegwerfen?");
        int index = scanner.nextInt();

        // check for index exception
        if (index <= 0 || index > highest.size()) {
            System.out.println("Falsche Eingabe");
            discard();

        } else {

            //remove the highest from current player that ended turn
            pc.getCurrentPlayer().getCards().remove(highest.get(index - 1));

            System.out.println("NACH WEGWURF ----------------");
            printPlayerHands();
        }
    }

    /**
     * Prints hands of all players
     */
    private void printPlayerHands() {
        // print player hands
        for (Player player : pc.getPlayers()) {
            System.out.println("Spieler: " + player.getName());
            player.printHand();
            System.out.println();
        }
    }


}
