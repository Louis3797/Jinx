package org.jinx.cardstack;

import static org.jinx.card.LuckyCardNames.*;
import static org.junit.jupiter.api.Assertions.*;

import org.jinx.card.LuckyCardNames;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class LuckyCardStackTest {

    /**
     * Test remove one luckycard from the deck and check if the deck is still valid
     */
    @Test
    void testLuckyCardStackremove() {
        var luckyCardStack = new LuckyCardStack();
        luckyCardStack.remove(1);
        assertEquals(11, luckyCardStack.size());
    }

    /**
     * Test if the method generateStandardDeck() is called
     */
    @Test
    void testLuckyCardStack() {
        var luckyCardStack = new LuckyCardStack();
        assertEquals(12, luckyCardStack.size());
    }

    /**
     * Test if the method luckyFactory() calls the right method for the right card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testLuckyFactoryLC456() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LC456;

        assertEquals(org.jinx.card.LC456.class, method.invoke(luckyCardStack, medthodArgruments).getClass());
    }

    /**
     * Test if the method luckyFactory() calls the right method for the right card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testLuckyFactoryLC123() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LC123;

        assertEquals(org.jinx.card.LC123.class, method.invoke(luckyCardStack, medthodArgruments).getClass());

    }

    /**
     * Test if the method luckyFactory() calls the right method for the right card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testLuckyFactoryLCMinus1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LCMinus1;

        assertEquals(org.jinx.card.LCMinus1.class, method.invoke(luckyCardStack, medthodArgruments).getClass());

    }

    /**
     * Test if the method luckyFactory() calls the right method for the right card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testLuckyFactoryLCPlus1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LCPlus1;

        assertEquals(org.jinx.card.LCPlus1.class, method.invoke(luckyCardStack, medthodArgruments).getClass());
    }

    /**
     * Test if the method luckyFactory() calls the right method for the right card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testLuckyFactoryLCPlusDicethrow() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LCPlusDicethrow;

        assertEquals(org.jinx.card.LCPlusDicethrow.class, method.invoke(luckyCardStack, medthodArgruments).getClass());
    }

    /**
     * Test if the method luckyFactory() calls the right method for the right card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testLuckyFactoryLCSum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = LCSum;

        assertEquals(org.jinx.card.LCSum.class, method.invoke(luckyCardStack, medthodArgruments).getClass());
    }

    @Test
    /**
     * Test if the method luckyFactory() calls argument exception for wrong card
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    void testLuckyCardNull() throws NoSuchMethodException {
        var luckyCardStack = new LuckyCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = LuckyCardNames.class;
        Method method = luckyCardStack.getClass().getDeclaredMethod("luckyFactory", parameters);
        method.setAccessible(true);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            method.invoke(luckyCardStack, "lC452");
        });
        assertEquals("argument type mismatch", exception.getMessage());
    }


}