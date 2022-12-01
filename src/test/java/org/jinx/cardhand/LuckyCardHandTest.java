package org.jinx.cardhand;

import org.jinx.card.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LuckyCardHandTest {

    LuckyCardHand luckyCardHand = new LuckyCardHand();

    @AfterEach
    void afterEach() {
        luckyCardHand.clear();
    }

    @Test
    void testNumberCardHandIsEmpty() {
        assertEquals(0, luckyCardHand.size());
    }

    /**
     * Tests has method with empty list
     */
    @Test
    void testHasLuckyCardWithEmptyLuckyCardList() {
        assertFalse(luckyCardHand.has(LuckyCardNames.LC123));
    }

    /**
     * Tests has method with same luckyCard in list
     */
    @Test
    void testHasLuckyCardWithSameLuckyCardInList() {
        luckyCardHand.add(new LC123());
        assertTrue(luckyCardHand.has(LuckyCardNames.LC123));
    }

    /**
     * Tests has method with other luckyCard in list
     */
    @Test
    void testHasLuckyCardWithOtherLuckyCardInList() {
        luckyCardHand.add(new LC456());
        assertFalse(luckyCardHand.has(LuckyCardNames.LC123));
    }

    /**
     * Tests count method with empty list
     */
    @Test
    void testcountLuckyCardsWithEmptyList() {
        assertEquals(0, luckyCardHand.count(LuckyCardNames.LC123));
    }

    /**
     * Tests count method with one same luckyCard in list
     */
    @Test
    void testcountLuckyCardsWithOneSameLuckyCardInList() {
        luckyCardHand.add(new LC123());
        assertEquals(1, luckyCardHand.count(LuckyCardNames.LC123));

    }

    /**
     * Tests count method with two same luckyCard in list
     */
    @Test
    void testcountLuckyCardsWithTwoSameLuckyCardInList() {
        luckyCardHand.add(new LC123());
        luckyCardHand.add(new LC123());
        assertEquals(2, luckyCardHand.count(LuckyCardNames.LC123));
    }

    /**
     * Tests count method with other luckyCard in list
     */
    @Test
    void testcountLuckyCardsWithOtherLuckyCardsInList() {
        luckyCardHand.add(new LC456());
        assertEquals(0, luckyCardHand.count(LuckyCardNames.LC123));
    }

    /**
     * Tests count method with a bunch of luckyCards in list
     */
    @Test
    void testcountLuckyCardsWithABunchOfLuckyCardsInList() {
        luckyCardHand.add(new LC123());
        luckyCardHand.add(new LC123());
        luckyCardHand.add(new LC456());
        luckyCardHand.add(new LCSum());
        luckyCardHand.add(new LCPlusDicethrow());
        assertEquals(2, luckyCardHand.count(LuckyCardNames.LC123));
    }

}
