package org.jinx.dice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
        @Test
        void testDice() {
            Dice dice = new Dice();
            dice.use();
        }
}