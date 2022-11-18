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
        this.setUsedCheats(true);
    }

    /**
     * Method finds the best card we can pick in the specified list of cards and the dice result
     *
     * @param cards List of number cards we can pick from
     * @return Returns the best card the player can pick
     */
    public int getIndexOfBestCard(List<NumberCard> cards) {

        if (cards.size() == 1) {
            return 0;
        }

        int indexOfBestCard = 0;
        int bestWeight = Integer.MIN_VALUE;

        for (int i = 0; i < cards.size(); i++) {
            for (Weight<NumberCard> cardWeight : numberCardWeightList) {
                if (cards.get(i).equals(cardWeight.object()) && bestWeight < cardWeight.weight()) {
                    indexOfBestCard = i;
                    bestWeight = cardWeight.weight();
                }
            }
        }

        return indexOfBestCard;
    }

    /**
     * Updates the numberCardWeightList variable
     */
    public void updateWeightOfNumberCards() {
        this.numberCardWeightList.clear();
        this.numberCardWeightList.addAll(calculateWeightOfNumberCardsOnField(this, calculateMostDangerousOpponent(this)));
    }

    /**
     * Method calculates the weight of each Card on the field
     * A high weight means that the Card is good a low weight means the card is bad
     *
     * @param opponent Most dangerous opponent
     * @return Returns a List with all Cards in the Field weighted
     */
    private List<Weight<NumberCard>> calculateWeightOfNumberCardsOnField(Player player, Player opponent) {

        List<Weight<NumberCard>> cardsWeights = new ArrayList<>();

        double avgPoints = calculateAveragePoints(Arrays.stream(field.getField()).toList());

        Map<CardColor, Double> opponentCardColorPercentageMap = opponent.getNumberCardHand().calculateCardColorPercentage();

        Map<CardColor, Double> playerCardColorPercentageMap = player.getNumberCardHand().calculateCardColorPercentage();


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

            StringBuilder reason = new StringBuilder();

            int weight = 0;

            CardColor color = card.getColor();

            int cardNumber = Integer.parseInt(card.getName());

            if (cardNumber >= avgPoints) {
                ++weight;
                reason.append("+1 Gewicht, da die Nummer der Karte ist größer oder gleich als der Durchschnitt ist\n");
            } else {
                --weight;
                reason.append("-1 Gewicht, da die Nummer der Karte ist kleiner als der Durchschnitt ist\n");
            }

            if (player.getNumberCardHand().containsColor(color)) {
                ++weight;
                reason.append("+1 Gewicht, da die Farbe der Karte in der Hand des Spielers vorkommt\n");
            } else {
                --weight;
                reason.append("-1 Gewicht, da die Farbe der Karte nicht in der Hand des Spielers vorkommt\n");
            }

            if (playerCardColorPercentageMap.get(color) != null && playerCardColorPercentageMap.get(color) >= 33.0) {
                ++weight;
                reason.append("+1 Gewicht, da die Farbe der Karte mehr oder gleich 33% der Spielerhand ausmacht\n");
            }


            // stored in extra variable, bc we need it twice
            boolean isColorInOpponentHand = opponent.getNumberCardHand().containsColor(color);

            if (isColorInOpponentHand) {
                --weight;
                reason.append("-1 Gewicht, da die Farbe der Karte in der Hand des gefährlichsten Spielers (").append(opponent.getName()).append(") vorkommt \n");
            } else {
                ++weight;
                reason.append("+1 Gewicht, da die Farbe der Karte nicht in der Hand des gefährlichsten Spielers (").append(opponent.getName()).append(") vorkommt \n");
            }

            if (opponentCardColorPercentageMap.get(color) != null && opponentCardColorPercentageMap.get(color) < 33.0) {
                ++weight;
                reason.append("+1 Gewicht, da die Farbe der Karte weniger als 33% der Hand des gefährlichsten Spielers (").append(opponent.getName()).append(") ausmacht\n");
            }

            int colorCount = field.countCardColor(color);


            if (colorCount <= rareCardConstant) {
                ++weight;
                reason.append("+1 Gewicht, da die Farbe der Karte nicht oft auf den Feld vorkommt\n");
            } else {
                --weight;
                reason.append("-1 Gewicht, da die Farbe der Karte oft auf den Feld vorkommt\n");
            }


            cardsWeights.add(new Weight<>(card, weight, reason.toString()));
        }

        cardsWeights.sort(Comparator.comparingInt(Weight::weight));
        Collections.reverse(cardsWeights);
        return cardsWeights;

    }

    /**
     * Calculates the most dangerous opponent by weighting all opponents according to 3 criteria.
     * 1. Number of cards >= average number of cards = weight + 1
     * 2. Opponent has more than or 3 different suits of cards in hand = weight + 1
     * 3. Sum of points >= average sum of points = weight + 1
     *
     * @param currentPlayer Current player we calculate for
     * @return Returns the Player object of the most dangerous opponent
     */
    public Player calculateMostDangerousOpponent(Player currentPlayer) {

        List<Weight<Player>> weightedOpponentList = new ArrayList<>();

        double averageCardAmountOfAllPlayers = calculateAverageCardAmountOfAllPlayers();

        for (Player opponent : playerController.getPlayers()) {
            if (!opponent.equals(currentPlayer)) {

                int weight = 0;

                int opponentCardAmount = opponent.getNumberCardHand().size();

                // check if opponent has more cards than the average player
                if (opponentCardAmount >= averageCardAmountOfAllPlayers) ++weight;
                else --weight;

                int amountOfDifferentCards = opponent.getNumberCardHand().calculateCardDiversity();

                // check if opponent has more than 3 different cards in his hand
                if (amountOfDifferentCards >= 3) ++weight;
                else --weight;

                // check if opponent has more points than the average
                if (calculateAveragePoints(opponent.getNumberCardHand()) >= calculateAveragePointsOfAllPlayers())
                    ++weight;
                else --weight;

                weightedOpponentList.add(new Weight<>(opponent, weight, ""));
            }
        }

        weightedOpponentList.sort(Comparator.comparingInt(Weight::weight));
        Collections.reverse(weightedOpponentList);

        return weightedOpponentList.get(0).object();
    }


    /**
     * Calculates the average amount of cards of all players in game
     *
     * @return Returns the average
     */
    private double calculateAverageCardAmountOfAllPlayers() {

        int sum = 0;

        for (Player player : playerController.getPlayers()) {
            sum += player.getNumberCardHand().size();
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
            numberOfCards += player.getNumberCardHand().size();

            for (NumberCard card : player.getNumberCardHand()) {
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
     * Method is used for discarding a card after a round ends
     *
     * @return Returns the index of the baddest card in the hand of the player
     */
    public int findIndexForTheBestCardToDiscard(List<NumberCard> cards) {

        if (cards.size() == 1) {
            return 0;
        }

        Player mdo = calculateMostDangerousOpponent(this);

        Map<CardColor, Double> map = mdo.getNumberCardHand().calculateCardColorPercentage();

        int index = 0;
        double highestPercentage = 0;

        for (int i = 0; i < cards.size(); i++) {

            if (map.get(cards.get(i).getColor()) != null) {
                double colorPercentage = map.get(cards.get(i).getColor());

                if (highestPercentage < colorPercentage) {
                    highestPercentage = colorPercentage;
                    index = i;
                }
            }
        }

        return index;
    }

    /**
     * Calculates if its usefully for the autonomous agent to roll the dice again
     *
     * @return Returns true if usefully
     */
    public boolean considerRollDiceAgain(Stack<Integer> diceResults) {
        // check if given dice results are brought us the best card based on difficulty:
        // difficulty == Easy: If one of the 5 best cards can be selected by the dice result, false is returned
        // difficulty == Medium: If one of the 3 best cards can be selected by the dice result, false is returned
        // difficulty == Hard: If best card can be picked with the results in the Stack, return false

        if (numberCardWeightList.isEmpty()) {
            return false;
        }

        int temp = difficulty == AgentDifficulty.EASY ? 5 : difficulty == AgentDifficulty.MEDIUM ? 3 : 1;

        for (int result : diceResults) {
            for (int i = 0; i < temp; i++) {
                if (temp >= numberCardWeightList.size()) {
                    return false;
                }
                Weight<NumberCard> card = numberCardWeightList.get(i);
                if (card != null)
                    if (card.object().getName().equals(Integer.toString(result))) {
                        return false;
                    }
            }
        }
        return true;
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

    public int getBestCardNumberForLCPickNumber(int min, int max) throws IllegalAccessException {

        if (min > max) {
            throw new IllegalAccessException("Parameter min cannot be higher than parameter max");
        }

        int bestNumber = 0;

        for (Weight<NumberCard> cardWeight : numberCardWeightList) {
            int number = Integer.parseInt(cardWeight.object().getName());
            if (number >= min && number <= max) {
                bestNumber = number;
                break;
            }
        }

        return bestNumber;
    }

    /**
     * Method checks if the use of the LuckyCard plus dice throw is usefully for the bot or not
     *
     * @return Returns true if the use of the LuckyCard is usefully, else returns false
     */
    public boolean considerUseOfLCPlusDiceThrow(Stack<Integer> diceResults) {
        return considerRollDiceAgain(diceResults);
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

        return compareCardWeightBetweenTwoDiceResults(diceResult, diceResult - 1);
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

        return compareCardWeightBetweenTwoDiceResults(diceResult, diceResult + 1);
    }

    /**
     * Helper method to find the highest card weight between two dice results
     *
     * @param oldDiceResult old dice result
     * @param newDiceResult new dice result
     * @return Returns true if the card we can pick with the new dice result has a
     * higher weight than the card with the old dice result
     */
    private boolean compareCardWeightBetweenTwoDiceResults(int oldDiceResult, int newDiceResult) {
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

        return diceStack.peek() != bestDiceResult;
    }

    /**
     * Method checks if the player should pick a lucky card
     *
     * @return Returns true if yes
     */
    public boolean considerPickLuckyCard() {

        if (getNumberCardHand().isEmpty() || getLuckyCardHand().size() > 3) {
            return false;
        }


        // Difficulty == Easy and
        // (amount of number cards -1 >= avg amount of number cards)
        // -> true
        if (difficulty == AgentDifficulty.EASY && getNumberCardHand().size() - 1 >= calculateAverageCardAmountOfAllPlayers()) {
            return true;
        }

        // Difficulty == Easy
        // and amount of number cards -1 >= avg amount of number cards
        // and point of player >= avg points
        // -> true
        if (difficulty == AgentDifficulty.MEDIUM && getNumberCardHand().size() - 1 >= calculateAverageCardAmountOfAllPlayers() && calculateAveragePoints(this.getNumberCardHand()) >= calculateAveragePointsOfAllPlayers()) {
            return true;
        }

        if (difficulty == AgentDifficulty.HARD) {
            if (getNumberCardHand().size() - 1 >= calculateAverageCardAmountOfAllPlayers() && calculateAveragePoints(this.getNumberCardHand()) >= calculateAveragePointsOfAllPlayers()) {
                for (NumberCard cards : getNumberCardHand()) {
                    if (Integer.parseInt(cards.getName()) <= 3) return true;
                }
            }
        }

        return false;
    }

    /**
     * Finds the best number card to trade for a lucky card
     *
     * @return Returns the index of the card
     */
    public int findCardForTrade() {

        int lowestCardIndex = 0;
        int lowestCardNumber = Integer.MAX_VALUE;

        for (int i = 0; i < getNumberCardHand().size(); i++) {
            int cardNumber = Integer.parseInt(getNumberCardHand().get(i).getName());
            if (lowestCardNumber > cardNumber) {
                lowestCardNumber = cardNumber;
                lowestCardIndex = i;
            }
        }

        return lowestCardIndex;
    }

    /**
     * Method checks if the use of the LuckyCard Sum is usefully for the player or not
     *
     * @return Returns if the use of the lucky card is usefully
     */
    public boolean considerUseOfLCSum() {

        // Easy condition
        if (this.difficulty == AgentDifficulty.EASY) {
            // return true if player has fewer cards than the average
            return getNumberCardHand().size() < calculateAverageCardAmountOfAllPlayers();

        } else if (this.difficulty == AgentDifficulty.MEDIUM) {
            // return true if player has fewer cards than the average and less than 3 colors on the hand
            return getNumberCardHand().size() < calculateAverageCardAmountOfAllPlayers() && getNumberCardHand().calculateCardDiversity() < 3;

        } else if (this.difficulty == AgentDifficulty.HARD) {
            // return true if player has fewer cards than the average and less than 3 colors on the hand or if there is a combination with only high weight cards
            return getNumberCardHand().size() < calculateAverageCardAmountOfAllPlayers() && getNumberCardHand().calculateCardDiversity() <= 3;
        }

        return false;
    }

    /**
     * Returns the index of the list with the best combination
     *
     * @param combinations 2 dimensional list of number cards
     * @return Returns the index of the list with the best combination
     */
    public int getIndexOfBestCardCombination(List<List<NumberCard>> combinations) {
        int highestSumWeight = 0;
        int index = 0;

        for (int i = 0; i < combinations.size(); i++) {

            int sum = 0;

            for (NumberCard card : combinations.get(i)) {

                for (Weight<NumberCard> cardWeight : numberCardWeightList) {

                    if (cardWeight.object().equals(card)) {
                        sum += cardWeight.weight();
                    }
                }

                if (sum > highestSumWeight) {
                    highestSumWeight = sum;
                    index = i;
                }
            }

        }

        return index;
    }

    /**
     * Returns the reasoning of the weight for a specific card
     *
     * @param card Given Number card
     * @return Returns the reason for the weight of the card exists and
     */
    public String getReasonForCard(NumberCard card) {

        for (Weight<NumberCard> cardWeight : numberCardWeightList) {
            if (cardWeight.object().equals(card)) {
                return cardWeight.reason();
            }
        }

        throw new IllegalArgumentException("Weight for Card does not exist!");
    }

    public NumberCard givePlayerTip(Player player, List<NumberCard> availableCards) {
        if (availableCards.size() == 1) {
            return availableCards.get(0);
        }

        List<Weight<NumberCard>> cardWeights = calculateWeightOfNumberCardsOnField(player, calculateMostDangerousOpponent(player));


        int index = 0;
        int bestWeight = Integer.MIN_VALUE;

        for (int i = 0; i < availableCards.size(); i++) {
            for (Weight<NumberCard> cardWeight : cardWeights) {
                if (availableCards.get(i).equals(cardWeight.object()) && bestWeight < cardWeight.weight()) {
                    index = i;
                    bestWeight = cardWeight.weight();
                }
            }
        }

        player.setUsedCheats(true);
        return availableCards.get(index);


    }

    @Override
    public boolean isHuman() {
        return false;
    }
}