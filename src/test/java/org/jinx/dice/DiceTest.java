package org.jinx.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    /**
     * Test if the dice is rolled correctly
     */
    @Test
    void testDice() {
        var dice = new Dice();
        int diceResult = dice.use();
        assertTrue(diceResult >= 1 && diceResult <= 6);
    }
}

