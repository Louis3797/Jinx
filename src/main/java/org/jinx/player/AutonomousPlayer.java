package org.jinx.player;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;
import org.jinx.field.Field;
import org.jinx.game.PlayerController;
import org.jinx.utils.Weight;

import java.util.*;

/**
 * The Autonomous Player Class represents a Player that can play the game on its own.
 * He can calculate his next move based on 3 difficulty levels
 */
public class AutonomousPlayer extends Player {

    /**
     * Player Controller instance
     */
    private final PlayerController playerController = PlayerController.getPlayerControllerInstance();

    /**
     * Field instance
     */
    private final Field field = Field.getFieldInstance();

    /**
     * Stores the difficulty of the bot
     */
    private final AgentDifficulty difficulty;

    private final List<Weight<NumberCard>> numberCardWeightList;


    /**
     * Standard Constructor for the Player
     *
     * @param name Name of the player
     */
    public AutonomousPlayer(String name, AgentDifficulty difficulty) {
        super(name);
        this.difficulty = difficulty;
        this.numberCardWeightList = new ArrayList<>();
    }

    /**
     * Calculates the next best Move that the Autonomous Player can do, by calculating the most dangerous opponent and
     */
    public List<NumberCard> calculateNextMove() {

        List<NumberCard> result = new ArrayList<>();

        Player mostDangerousOpponent = calculateMostDangerousOpponent();

        List<Weight<NumberCard>> cardsWeights = calculateWeightOfNumberCardsOnField(mostDangerousOpponent);


        return result;
    }

    /**
     * Updates the numberCardWeightList variable
     */
    public void updateWeightOfNumberCards() {
        this.numberCardWeightList.clear();
        this.numberCardWeightList.addAll(calculateWeightOfNumberCardsOnField(calculateMostDangerousOpponent()));
    }

    /**
     * Method calculates the weight of each Card on the field
     * A high weight means that the Card is good a low weight means the card is bad
     *
     * @param opponent Most dangerous opponent
     * @return Returns a List with all Cards in the Field weighted
     */
    private List<Weight<NumberCard>> calculateWeightOfNumberCardsOnField(Player opponent) {

        List<Weight<NumberCard>> cardsWeights = new ArrayList<>();

        double avgPoints = calculateAveragePoints(Arrays.stream(field.getField()).toList());

        Map<CardColor, Double> opponentCardColorPercentageMap = calculateCardColorPercentage(opponent.getCards());

        Map<CardColor, Double> playerCardColorPercentageMap = calculateCardColorPercentage(this.getCards());


        int numberOfCardsInField = 0;

        for (NumberCard card : field.getField()) {
            if (card != null) numberOfCardsInField++;
        }

        // defines if a card color is rare in the field
        int rareCardConstant = (int) Math.floor(Math.abs(((numberOfCardsInField / 4) - (playerController.getPlayers().size() - 2))));

        for (NumberCard card : field.getField()) {

            // continue if the card is null
            if (card == null)
                continue;

            int weight = 0;

            CardColor color = card.getColor();

            int cardNumber = Integer.parseInt(card.getName());

            if (cardNumber >= avgPoints) ++weight;
            else --weight;

            if (isCardColorInPlayerHand(this, color)) ++weight;
            else --weight;

            if (playerCardColorPercentageMap.get(color) != null && playerCardColorPercentageMap.get(color) >= 33.0)
                ++weight;

            // stored in extra variable, bc we need it twice
            boolean isColorInOpponentHand = isCardColorInPlayerHand(opponent, color);

            if (isColorInOpponentHand) --weight;
            else ++weight;

            if (opponentCardColorPercentageMap.get(color) != null && opponentCardColorPercentageMap.get(color) < 33.0)
                ++weight;

            int colorCount = countCardColor(Arrays.stream(field.getField()).toList(), color);


            if (colorCount <= rareCardConstant) ++weight;
            else --weight;


            cardsWeights.add(new Weight<>(card, weight));
        }

        cardsWeights.sort(Comparator.comparingInt(Weight::weight));
        Collections.reverse(cardsWeights);
        return cardsWeights;

    }

    /**
     * Counts how much the card color occurs in the specified list
     *
     * @param cards List of NumberCards
     * @param color Specified card color
     * @return Returns how often the color occurs in the list
     */
    private int countCardColor(List<NumberCard> cards, CardColor color) {
        int sum = 0;
        for (NumberCard card : cards) {
            if (card != null && card.getColor().equals(color)) ++sum;
        }
        return sum;
    }

    /**
     * Checks if specified card color is in given players hand
     *
     * @param player Specified player
     * @param color  Color of Card we want to check
     * @return Returns true if card occurs in player hand, else false
     */
    private boolean isCardColorInPlayerHand(Player player, CardColor color) {
        for (NumberCard card : player.getCards()) if (card.getColor().equals(color)) return true;
        return false;
    }

    /**
     * Calculates the percentage occurrence of the card colors in the specified list
     *
     * @param cards List of Numbercards
     * @return Returns a Map with the CardColor as Key and the occurrence percentage as value of all card colors in the specified list
     */
    private Map<CardColor, Double> calculateCardColorPercentage(List<NumberCard> cards) {

        Map<CardColor, Double> map = new HashMap<>();
        int numberOfCards = 0;

        for (NumberCard card : cards) {
            if (card == null)
                continue;
            ++numberOfCards;
            CardColor color = card.getColor();
            map.put(color, map.getOrDefault(color, 0.0) + 1);

        }

        // this is needed because of the lambda expression
        int finalNumberOfCards = numberOfCards;
        map.replaceAll((c, v) -> (map.get(c) / finalNumberOfCards) * 100);

        return map;
    }


    /**
     * Calculates the most dangerous opponent by weighting all opponents according to 3 criteria.
     * 1. Number of cards >= average number of cards = weight + 1
     * 2. Opponent has more than or 3 different suits of cards in hand = weight + 1
     * 3. Sum of points >= average sum of points = weight + 1
     *
     * @return Returns the Player object of the most dangerous opponent
     */
    public Player calculateMostDangerousOpponent() {

        List<Weight<Player>> weightedOpponentList = new ArrayList<>();

        double averageCardAmountOfAllPlayers = calculateAverageCardAmountOfAllPlayers();

        for (Player opponent : playerController.getPlayers()) {
            if (!opponent.equals(this)) {

                int weight = 0;

                int opponentCardAmount = opponent.getCards().size();

                // check if opponent has more cards than the average player
                if (opponentCardAmount >= averageCardAmountOfAllPlayers) ++weight;

                int amountOfDifferentCards = calculateCardDiversity(opponent.getCards());

                // check if opponent has more than 3 different cards in his hand
                if (amountOfDifferentCards >= 3) ++weight;

                // check if opponent has more points than the average
                if (calculateAveragePoints(opponent.getCards()) >= calculateAveragePointsOfAllPlayers()) ++weight;

                weightedOpponentList.add(new Weight<>(opponent, weight));
            }
        }

        weightedOpponentList.sort(Comparator.comparingInt(Weight::weight));
        Collections.reverse(weightedOpponentList);

        return weightedOpponentList.get(0).object();
    }

    /**
     * Calculates how many card colors are contained in the given card list
     *
     * @param cards List of NumberCards
     * @return Returns how many card colors are contained in the given card list
     */
    private int calculateCardDiversity(List<NumberCard> cards) {
        Set<CardColor> set = new HashSet<>();

        for (NumberCard card : cards) set.add(card.getColor());

        return set.size();
    }

    /**
     * Calculates the average amount of cards of all players in game
     *
     * @return Returns the average
     */
    private double calculateAverageCardAmountOfAllPlayers() {

        int sum = 0;

        for (Player player : playerController.getPlayers()) {
            sum += player.getCards().size();
        }

        return (double) sum / playerController.getPlayers().size();
    }

    /**
     * Calculates the average points of all players in game
     *
     * @return Returns average points of all players
     */
    private double calculateAveragePointsOfAllPlayers() {

        int sumOfAllPoints = 0;
        int numberOfCards = 0;

        for (Player player : playerController.getPlayers()) {
            numberOfCards += player.getCards().size();

            for (NumberCard card : player.getCards()) {
                sumOfAllPoints += Integer.parseInt(card.getName());
            }
        }

        return (double) sumOfAllPoints / numberOfCards;
    }

    /**
     * Calculates the average points of the given list of cards
     *
     * @param cards List of NumberCards
     * @return Returns the average points of all cards in the given list
     */
    private double calculateAveragePoints(List<NumberCard> cards) {

        double average = 0.0;
        int numberOfCards = 0;

        for (NumberCard card : cards) {
            if (card != null) {
                numberOfCards++;
                average += Integer.parseInt(card.getName());
            }
        }

        return average / numberOfCards;
    }

    /**
     * Calculates if its usefully for the autonomous agent to roll the dice again
     *
     * @return Returns true if usefully
     */
    public boolean considerRollDiceAgain() {
        // check if first dice roll brought us the best card
        return true;
    }

    /**
     * Calculates if it's good if the round ends now
     *
     * @return Returns true if the outcome of the end of the round is good for us
     */
    public boolean considerEndRound() {

        // true if player have the most points after discarding the cards


        return false;
    }

    /**
     * Method checks if the use of the LuckyCard 123 is usefully for the bot or not
     *
     * @return Returns true if the use of the LuckyCard is usefully, else returns false
     */
    public boolean considerUseOfLC123() {

        for (Weight<NumberCard> cardWeight : numberCardWeightList) {

            int cardNumber = Integer.parseInt(cardWeight.object().getName());

            // A good card weight is decided by the difficulty level
            int goodCardWeight = this.difficulty == AgentDifficulty.EASY ? 3 :
                    this.difficulty == AgentDifficulty.MEDIUM ? 4 : 5;

            if ((cardNumber >= 1 && cardNumber <= 3) && cardWeight.weight() == goodCardWeight) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method checks if the use of the LuckyCard 456 is usefully for the bot or not
     *
     * @return Returns true if the use of the LuckyCard is usefully, else returns false
     */
    public boolean considerUseOfLC456() {

        for (Weight<NumberCard> cardWeight : numberCardWeightList) {

            int cardNumber = Integer.parseInt(cardWeight.object().getName());

            // A good card weight is decided by the difficulty level
            int goodCardWeight = this.difficulty == AgentDifficulty.EASY ? 3 :
                    this.difficulty == AgentDifficulty.MEDIUM ? 4 : 5;

            if ((cardNumber >= 4 && cardNumber <= 6) && cardWeight.weight() == goodCardWeight) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method checks if the use of the LuckyCard plus dice throw is usefully for the bot or not
     *
     * @return Returns true if the use of the LuckyCard is usefully, else returns false
     */
    public boolean considerUseOfLCPlusDiceThrow() {
        // card can be used more than one time, so it's always good to roll the dice again
        return true;
    }

    /**
     * Method checks if the use of the LuckyCard minus 1 is usefully for the bot or not
     *
     * @param diceResult Result of the dice roll
     * @return Returns true if the use of the LuckyCard is usefully, else returns false
     */
    public boolean considerUseOfLCMinus1(int diceResult) {
        if (diceResult - 1 < 1)
            return false;

        return compateCardWeightBetweenTwoDiceResults(diceResult, diceResult - 1);
    }

    /**
     * Method checks if the use of the LuckyCard plus 1 is usefully for the bot or not
     *
     * @param diceResult Result of the dice roll
     * @return Returns true if the use of the LuckyCard is usefully, else returns false
     */
    public boolean considerUseOfLCPlus1(int diceResult) {
        if (diceResult + 1 > 6)
            return false;

        return compateCardWeightBetweenTwoDiceResults(diceResult, diceResult + 1);
    }

    /**
     * Helper method to find the highest card weight between two dice results
     *
     * @param oldDiceResult old dice result
     * @param newDiceResult new dice result
     * @return Returns true if the card we can pick with the new dice result has a
     * higher weight than the card with the old dice result
     */
    private boolean compateCardWeightBetweenTwoDiceResults(int oldDiceResult, int newDiceResult) {
        int highestWeightWithOldDiceResult = Integer.MIN_VALUE, highestWeightWithNewDiceResult = Integer.MIN_VALUE;

        for (Weight<NumberCard> cardWeight : numberCardWeightList) {

            int cardNumber = Integer.parseInt(cardWeight.object().getName());

            if (cardNumber == oldDiceResult && cardWeight.weight() > highestWeightWithOldDiceResult)
                highestWeightWithOldDiceResult = cardWeight.weight();

            if (cardNumber == newDiceResult && cardWeight.weight() > highestWeightWithNewDiceResult)
                highestWeightWithNewDiceResult = cardWeight.weight();
        }

        return highestWeightWithNewDiceResult > highestWeightWithOldDiceResult;
    }

    /**
     * Method checks if the undo function allows the selection of a better card
     *
     * @param diceStack Stack of all dice results
     * @return Returns true if the peek of the stack brings us not the best card
     */
    public boolean considerUseOfUndo(Stack<Integer> diceStack) {

        // Debugging
        System.out.print("[ ");
        for (Weight<NumberCard> cardWeight : numberCardWeightList) {
            System.out.print("( weight: " + cardWeight.weight() + ", card number: " + cardWeight.object().getName() + ", color: " + cardWeight.object().getColor() + " ) ");
        }
        System.out.println(" ]");

        // simple cases
        if (diceStack.size() <= 1) {
            return false;
        }

        List<Integer> diceResultsList = diceStack.stream().toList();

        int bestDiceResult = 0;
        int bestCardWeight = Integer.MIN_VALUE;

        // find the best available card weight with the available dice results in the list
        for (Integer result : diceResultsList) {
            for (Weight<NumberCard> cardWeight : numberCardWeightList) {

                int cardNumber = Integer.parseInt(cardWeight.object().getName());

                if (result == cardNumber && bestCardWeight < cardWeight.weight()) {
                    bestCardWeight = cardWeight.weight();
                    bestDiceResult = result;
                }
            }
        }

        System.out.println("BestDiceResult: " + bestDiceResult);
        System.out.println("Peek: " + diceStack.peek());
        System.out.println(diceStack.peek() == bestDiceResult);
        return diceStack.peek() != bestDiceResult;
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}