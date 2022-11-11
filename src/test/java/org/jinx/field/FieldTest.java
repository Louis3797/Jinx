package org.jinx.field;

import static org.junit.jupiter.api.Assertions.*;

import org.jinx.card.NumberCard;
import org.jinx.cardstack.NumberCardStack;
import org.junit.jupiter.api.Test;

class FieldTest {
    @Test
    void testField() {
        Field field = new Field();
        assertEquals(0, field.getAvailableNumberCards(3).size());
    }

    @Test
    void testField2() {
        Field field = new Field();
        NumberCardStack numberCardStack = new NumberCardStack();
        field.setField(numberCardStack);
        assertTrue(field.getAvailableNumberCards(3).size() >= 1 && field.getAvailableNumberCards(3).size() <= 6);

    }

    @Test
    void testRemoveChosenCard() {
        Field field = new Field();
        NumberCardStack numberCardStack = new NumberCardStack();
        field.setField(numberCardStack);
        field.removeChosenCard(field.getField()[0]);
        assertEquals(null, field.getField()[0]);

    }
}