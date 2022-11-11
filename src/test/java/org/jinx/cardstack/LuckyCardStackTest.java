package org.jinx.cardstack;

import static org.jinx.card.LuckyCardNames.LC456;
import static org.junit.jupiter.api.Assertions.*;


import org.jinx.card.LC123;
import org.jinx.card.LC456;
import org.jinx.card.LuckyCard;
import org.jinx.card.LuckyCardNames;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        assertEquals(12, luckyCardStack.size());
    }

    @Test
    void testLuckyCardStack3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LuckyCardStack luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LC456;

        assertEquals(new LC456().getClass(),  method.invoke(luckyCardStack, medthodArgruments).getClass());

    }
}