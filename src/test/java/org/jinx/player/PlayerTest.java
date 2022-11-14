package org.jinx.player;

import org.jinx.card.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player = new Player("Bob");

    @Test
    void testIfNumberCardListIsEmpty() {
        assertEquals(0, player.getCards().size());
    }

    @Test
    void testIfLuckyCardListIsEmpty() {
        assertEquals(0, player.getLuckyCards().size());
    }

    @Test
    void testIfPlayerIsHuman() {
        assertTrue(player.isHuman()); // should be true everytime, bc return true is hardcoded
    }

    @Test
    void testHasLuckyCardWithEmptyLuckyCardList() {
        assertFalse(player.hasLuckyCard(LuckyCardNames.LC123));
    }

    @Test
    void testHasLuckyCardWithSameLuckyCardInList() {
        player.getLuckyCards().add(new LC123());
        assertTrue(player.hasLuckyCard(LuckyCardNames.LC123));
    }

    @Test
    void testHasLuckyCardWithOtherLuckyCardInList() {
        player.getLuckyCards().add(new LC456());
        assertFalse(player.hasLuckyCard(LuckyCardNames.LC123));
    }

    @Test
    void testcountLuckyCardsWithEmptyList() {
        assertEquals(0, player.countLuckyCards(LuckyCardNames.LC123));
    }

    @Test
    void testcountLuckyCardsWithOneSameLuckyCardInList() {
        player.getLuckyCards().add(new LC123());
        assertEquals(1, player.countLuckyCards(LuckyCardNames.LC123));

    }

    @Test
    void testcountLuckyCardsWithTwoSameLuckyCardInList() {
        player.getLuckyCards().add(new LC123());
        player.getLuckyCards().add(new LC123());
        assertEquals(2, player.countLuckyCards(LuckyCardNames.LC123));
    }

    @Test
    void testcountLuckyCardsWithOtherLuckyCardsInList() {
        player.getLuckyCards().add(new LC456());
        assertEquals(0, player.countLuckyCards(LuckyCardNames.LC123));
    }

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