package org.jinx.player;

import org.jinx.card.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private final Player player = new Player("Bob");

    /**
     * Tests if size of number card list of player is 0
     */
    @Test
    void testIfNumberCardListIsEmpty() {
        assertEquals(0, player.getCards().size());
    }

    /**
     * Tests if size of lucky card list of player is 0
     */
    @Test
    void testIfLuckyCardListIsEmpty() {
        assertEquals(0, player.getLuckyCards().size());
    }

    /**
     * Tests if player is human -> return is hardcoded so its maybe irrelevant
     */
    @Test
    void testIfPlayerIsHuman() {
        assertTrue(player.isHuman()); // should be true everytime, bc return true is hardcoded
    }

    /**
     * Tests hasLuckyCard method in player with empty lucky card list
     */
    @Test
    void testHasLuckyCardWithEmptyLuckyCardList() {
        assertFalse(player.hasLuckyCard(LuckyCardNames.LC123));
    }

    /**
     * Tests hasLuckyCard method in player with same luckyCard in lucky card list
     */
    @Test
    void testHasLuckyCardWithSameLuckyCardInList() {
        player.getLuckyCards().add(new LC123());
        assertTrue(player.hasLuckyCard(LuckyCardNames.LC123));
    }

    /**
     * Tests hasLuckyCard method in player with other luckyCard in lucky card list
     */
    @Test
    void testHasLuckyCardWithOtherLuckyCardInList() {
        player.getLuckyCards().add(new LC456());
        assertFalse(player.hasLuckyCard(LuckyCardNames.LC123));
    }

    /**
     * Tests countLuckyCards method in player with empty lucky card list
     */
    @Test
    void testcountLuckyCardsWithEmptyList() {
        assertEquals(0, player.countLuckyCards(LuckyCardNames.LC123));
    }

    /**
     * Tests countLuckyCards method in player with one same luckyCard in lucky card list
     */
    @Test
    void testcountLuckyCardsWithOneSameLuckyCardInList() {
        player.getLuckyCards().add(new LC123());
        assertEquals(1, player.countLuckyCards(LuckyCardNames.LC123));

    }

    /**
     * Tests countLuckyCards method in player with two same luckyCard in lucky card list
     */
    @Test
    void testcountLuckyCardsWithTwoSameLuckyCardInList() {
        player.getLuckyCards().add(new LC123());
        player.getLuckyCards().add(new LC123());
        assertEquals(2, player.countLuckyCards(LuckyCardNames.LC123));
    }

    /**
     * Tests countLuckyCards method in player with other luckyCard in lucky card list
     */
    @Test
    void testcountLuckyCardsWithOtherLuckyCardsInList() {
        player.getLuckyCards().add(new LC456());
        assertEquals(0, player.countLuckyCards(LuckyCardNames.LC123));
    }

    /**
     * Tests countLuckyCards method in player with a bunch of luckyCards in lucky card list
     */
    @Test
    void testcountLuckyCardsWithABunchOfLuckyCardsInList() {
        player.getLuckyCards().add(new LC123());
        player.getLuckyCards().add(new LC123());
        player.getLuckyCards().add(new LC456());
        player.getLuckyCards().add(new LCSum());
        player.getLuckyCards().add(new LCPlusDicethrow());
        assertEquals(2, player.countLuckyCards(LuckyCardNames.LC123));
    }

}