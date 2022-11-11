package org.jinx.dice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiceTest {
    @Test
    void testDice() {
        var dice = new Dice();
        int i = dice.use();
        assertEquals(true, i >= 1 && i <= 6);
    }
}

