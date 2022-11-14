package org.jinx.field;

import static org.junit.jupiter.api.Assertions.*;
import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;
import org.junit.jupiter.api.Test;

import java.util.List;

class FieldTest {

    /**
     * Tests if the method setField() and getAvailableNumberCards() work correctly
     */
    @Test
    void testsetField() {
        var field = Field.getFieldInstance();
        var numberCardStack = new NumberCardStack();
        field.setField(numberCardStack);
        assertTrue(field.getField().length == 16);

    }

    /**
     * Tests if the method removeChosenCard() works correctly
     */
    @Test
    void testRemoveChosenCard() {
        var field = Field.getFieldInstance();
        var numberCardStack = new NumberCardStack();
        field.setField(numberCardStack);
        List<NumberCard> availableCards = field.getAvailableNumberCards(1);
        NumberCard card = availableCards.get(0);
        field.removeChosenCard(card);
        assertEquals(1, field.getAvailableNumberCards(1).size());

    }
    /**
     * Tests if the method removeChosenCard() works correctly, if the card is not on the field
     */
    @Test
    void testremoveChosenCard(){
        var field = Field.getFieldInstance();
        var numberCardStack = new NumberCardStack();
        field.setField(numberCardStack);
        List<NumberCard> availableCards = field.getAvailableNumberCards(1);
        field.removeChosenCard(availableCards.get(1));
        field.removeChosenCard(availableCards.get(0));
        assertTrue(field.getAvailableNumberCards(1).isEmpty());
    }

    /**
     * Tests if the method testprintAvailableCards() works correctly
     */
    @Test
    void testprintAvailableCards() {
        var field = Field.getFieldInstance();
        var numberCardStack = new NumberCardStack();
        field.setField(numberCardStack);
        List<NumberCard> availableNumberCards = field.getAvailableNumberCards(1);
        field.printAvailableCards(availableNumberCards);
        assertFalse(field.getAvailableNumberCards(1).isEmpty());

    }

}