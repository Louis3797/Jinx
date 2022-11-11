package org.jinx.cardstack;

import static org.junit.jupiter.api.Assertions.*;


import org.jinx.card.LuckyCard;
import org.jinx.card.LuckyCardNames;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class LuckyCardStackTest {
    @Test
    void testLuckyCardStack() {
        var luckyCardStack = new LuckyCardStack();
        luckyCardStack.remove(1);
        assertEquals(11, luckyCardStack.size());
    }

    @Test
    void testLuckyCardStack2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        assertEquals(12,luckyCardStack.size());
    }


}