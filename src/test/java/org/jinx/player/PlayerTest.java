package org.jinx.player;

import org.jinx.card.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private final Player player = new Player("Bob");

    /**
     * Tests if size of numberCardHand of player is 0
     */
    @Test
    void testIfNumberCardHandIsEmpty() {
        assertEquals(0, player.getNumberCardHand().size());
    }

    /**
     * Tests if size of luckyCardHand of player is 0
     */
    @Test
    void testIfLuckyCardHandIsEmpty() {
        assertEquals(0, player.getLuckyCardHand().size());
    }

    /**
     * Tests if player is human -> return is hardcoded so its maybe irrelevant
     */
    @Test
    void testIfPlayerIsHuman() {
        assertTrue(player.isHuman()); // should be true everytime, bc return true is hardcoded
    }

}