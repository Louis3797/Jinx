package org.jinx.cardstack;

import static org.jinx.card.LuckyCardNames.*;
import static org.junit.jupiter.api.Assertions.*;
import org.jinx.card.LuckyCardNames;
import org.junit.jupiter.api.Test;
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
    void testLuckyCardStack2() {
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

        assertEquals(org.jinx.card.LC456.class,  method.invoke(luckyCardStack, medthodArgruments).getClass());
    }
    @Test
    void testLuckyCardStack4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LuckyCardStack luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LC123;

        assertEquals(org.jinx.card.LC123.class,  method.invoke(luckyCardStack, medthodArgruments).getClass());

    }
    @Test
    void testLuckyCardStack5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LuckyCardStack luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LCMinus1;

        assertEquals(org.jinx.card.LCMinus1.class,  method.invoke(luckyCardStack, medthodArgruments).getClass());

    }

    @Test

    void testLuckyCardStack6() throws NoSuchMethodException {
        LuckyCardStack luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            method.invoke(luckyCardStack, "lC452");
        });
        assertEquals("argument type mismatch", exception.getMessage());

    }

}