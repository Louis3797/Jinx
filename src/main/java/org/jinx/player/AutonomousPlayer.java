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

    private final PlayerController playerController = PlayerController.getPlayerControllerInstance();

    private final Field field = Field.getFieldInstance();

    /**
     * Stores the difficulty of the bot
     */
    private final AgentDifficulty difficulty;

    /**
     * Standard Constructor for the Player
     *
     * @param name Name of the player
     */
    public AutonomousPlayer(String name, AgentDifficulty difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

    /**
     * Calculates the next best Move that the Autonomous Player can do, by calculating the most dangerous opponent and
     */
    public List<NumberCard> calculateNextMove() {

        List<NumberCard> result = new ArrayList<>();

        Player mostDangerousOpponent = calculateMostDangerousOpponent();

        List<Weight<NumberCard>> cardsWeights = calculateWeightOfCards(mostDangerousOpponent);


        return result;
    }

    private List<Weight<NumberCard>> calculateWeightOfCards(Player opponent) {

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
        return cardsWeights;

    }

    private int countCardColor(List<NumberCard> cards, CardColor color) {
        int sum = 0;
        for (NumberCard card : cards) {
            if (card.getColor().equals(color)) ++sum;
        }
        return sum;
    }

    private boolean isCardColorInPlayerHand(Player player, CardColor color) {
        for (NumberCard card : player.getCards()) if (card.getColor().equals(color)) return true;
        return false;
    }

    private Map<CardColor, Double> calculateCardColorPercentage(List<NumberCard> cards) {

        Map<CardColor, Double> map = new HashMap<>();
        int numberOfCards = 0;

        for (NumberCard card : cards) {
            if (card == null)
                continue;
            ++numberOfCards;
            CardColor color = CardColor.valueOf(card.getName());
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

}
