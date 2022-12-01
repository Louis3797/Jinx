package org.jinx.player;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testGetPointsWithEmptyHand() {
        assertEquals(0, player.getPoints());
    }

    @Test
    void testGetPointsWithNullHand() {
        player.getNumberCardHand().add(null);
        assertEquals(0, player.getPoints());
    }

    @Test
    void testGetPointsWithCardsInHand() {
        player.getNumberCardHand().add(new NumberCard("1", CardColor.GREEN));
        player.getNumberCardHand().add(new NumberCard("2", CardColor.GREEN));
        player.getNumberCardHand().add(new NumberCard("3", CardColor.GREEN));
        player.getNumberCardHand().add(new NumberCard("4", CardColor.GREEN));
        assertEquals(10, player.getPoints());
    }
}