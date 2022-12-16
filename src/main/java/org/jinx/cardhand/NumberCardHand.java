package org.jinx.cardhand;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberCardHand extends CardHand<NumberCard> {

    public static final long serialVersionUID = 42L;
    /**
     * Calculates how many card colors are contained in the given card list
     *
     * @return Returns how many card colors are contained in the given card list
     */
    public int getColorDiversity() {
        Set<CardColor> set = new HashSet<>();

        for (NumberCard card : this) set.add(card.getColor());

        return set.size();
    }

    /**
     * Calculates the percentage occurrence of the card colors in the hand
     *
     * @return Returns a Map with the CardColor as Key and the occurrence percentage as value of all card colors in the specified list
     */
    public Map<CardColor, Double> getColorPercentage() {

        Map<CardColor, Double> map = new HashMap<>();
        int numberOfCards = 0;

        for (NumberCard card : this) {
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
     * Checks if specified card color occurs in hand
     *
     * @param color Specified Card color
     * @return Returns true if card color occurs in hand
     */
    public boolean containsColor(CardColor color) {
        for (NumberCard card : this) if (card.getColor().equals(color)) return true;
        return false;
    }

    /**
     * Counts how much the card color occurs in the hand
     * @param color Specified card color
     * @return Returns how often the color occurs in the list
     */
    public int countCardColor(CardColor color) {
        int sum = 0;
        for (NumberCard card : this) {
            if (card != null && card.getColor().equals(color)) ++sum;
        }
        return sum;
    }

    @Override
    public void print() {
        NumberCard.printFormatedNumberCards(this);
    }

    public String toString(){

        StringBuilder numberCards = new StringBuilder();

        for(NumberCard card : this){
            numberCards.append(card.getName()).append(" -> ").append(card.getColor()).append("\n");
        }
        return numberCards.toString();
    }
}
