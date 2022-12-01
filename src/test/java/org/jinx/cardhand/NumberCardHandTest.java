package org.jinx.cardhand;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NumberCardHandTest {

    NumberCardHand numberCardHand = new NumberCardHand();

    @AfterEach
    void afterEach() {
        numberCardHand.clear();
    }

    @Test
    void testNumberCardHandIsEmpty() {
        assertEquals(0, numberCardHand.size());
    }

    @Test
    void testCalculateCardDiversityWithEmptyHand() {
        assertEquals(0, numberCardHand.getColorDiversity());
    }

    @Test
    void testCalculateCardDiversityWithOneColorInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        assertEquals(1, numberCardHand.getColorDiversity());
    }

    @Test
    void testCalculateCardDiversityWithTwoSameColorsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("2", CardColor.GREEN));
        assertEquals(1, numberCardHand.getColorDiversity());
    }

    @Test
    void testCalculateCardDiversityWithTwoDifferentColorsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("1", CardColor.RED));
        assertEquals(2, numberCardHand.getColorDiversity());
    }

    @Test
    void testCalculateCardDiversityWithAllColorsInHand() {

        for (CardColor color : CardColor.values()) {
            numberCardHand.add(new NumberCard("1", color));
        }

        assertEquals(CardColor.values().length, numberCardHand.getColorDiversity());
    }

    @Test
    void testGetColorPercentageWithEmptyHand() {
        Map<CardColor, Double> map = numberCardHand.getColorPercentage();
        assertTrue(map.isEmpty());
    }

    @Test
    void testColorPercentageWithNullInHand() {
        numberCardHand.add(null);

        Map<CardColor, Double> map = numberCardHand.getColorPercentage();

        assertTrue(map.isEmpty());
    }

    @Test
    void testColorPercentageWithOneCardInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));

        Map<CardColor, Double> map = numberCardHand.getColorPercentage();

        assertEquals(1, map.size());
    }

    @Test
    void testColorPercentageCheckPercentageWithOneCardInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));

        Map<CardColor, Double> map = numberCardHand.getColorPercentage();

        assertEquals(100.0, map.get(CardColor.GREEN));
    }

    @Test
    void testColorPercentageCheckPercentageWithTwoSameCardsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("2", CardColor.GREEN));

        Map<CardColor, Double> map = numberCardHand.getColorPercentage();

        assertEquals(100.0, map.get(CardColor.GREEN));
    }

    @Test
    void testColorPercentageCheckPercentageWithTwoCardsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("1", CardColor.RED));

        Map<CardColor, Double> map = numberCardHand.getColorPercentage();

        assertEquals(50.0, map.get(CardColor.RED));
    }

    @Test
    void testColorPercentageCheckPercentageWithThreeCardsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("1", CardColor.RED));
        numberCardHand.add(new NumberCard("1", CardColor.BLUE));

        Map<CardColor, Double> map = numberCardHand.getColorPercentage();

        assertEquals(33.33333333333333, map.get(CardColor.RED));
    }

    @Test
    void testContainsColorWithEmptyHand() {
        assertFalse(numberCardHand.containsColor(CardColor.GREEN));
    }

    @Test
    void testContainsColorWithSameCardColor() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        assertTrue(numberCardHand.containsColor(CardColor.GREEN));
    }

    @Test
    void testContainsColorWithDifferentCardColor() {
        numberCardHand.add(new NumberCard("1", CardColor.RED));
        assertFalse(numberCardHand.containsColor(CardColor.GREEN));
    }

    @Test
    void testCountCardColorWithEmptyHand() {
        assertEquals(0, numberCardHand.countCardColor(CardColor.GREEN));
    }


    @Test
    void testCountCardColorWithOneSameColorInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        assertEquals(1, numberCardHand.countCardColor(CardColor.GREEN));

    }

    @Test
    void testCountCardColorWithTwoSameColorsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("2", CardColor.GREEN));
        assertEquals(2, numberCardHand.countCardColor(CardColor.GREEN));
    }

    @Test
    void testCountCardColorWithOtherColorsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.RED));
        assertEquals(0, numberCardHand.countCardColor(CardColor.GREEN));
    }


    @Test
    void testCountCardColorWithABunchOfColorsInHand() {
        numberCardHand.add(new NumberCard("1", CardColor.GREEN));
        numberCardHand.add(new NumberCard("2", CardColor.GREEN));
        numberCardHand.add(new NumberCard("1", CardColor.RED));
        numberCardHand.add(new NumberCard("1", CardColor.BLUE));
        numberCardHand.add(new NumberCard("1", CardColor.YELLOW));
        numberCardHand.add(new NumberCard("1", CardColor.CYAN));
        assertEquals(2, numberCardHand.countCardColor(CardColor.GREEN));
    }
}
