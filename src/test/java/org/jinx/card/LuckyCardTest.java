package org.jinx.card;

import org.jinx.wrapper.SafeScanner;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LuckyCardTest {
    @Test
    void testLCPlusDicetrow() {
        LuckyCard luckyCard = new LCPlusDicethrow();
        //test if the effect is correct espected number is 1-6
        assertTrue(luckyCard.effect() >= 1 && luckyCard.effect() <= 6);
    }

    @Test
    void testLCPlus1() {
        LuckyCard luckyCard = new LCPlus1();
        //test if the effect is correct espected number is 1
        assertEquals(1, luckyCard.effect());
    }

    @Test
    void testLCMinus1() {
        LuckyCard luckyCard = new LCMinus1();
        //test if the effect is correct espected number is -1
        assertEquals(-1, luckyCard.effect());
    }



}