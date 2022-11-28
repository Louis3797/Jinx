package org.jinx.cardstack;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberCardStackTest {

    private NumberCardStack numberCardStack;

    @BeforeEach
    void beforeEach() {
        numberCardStack = new NumberCardStack();
    }

    /**
     * Test generateDeck() method
     */
    @Test
    void testNumberCardStackInitialization() {
        assertEquals(48, numberCardStack.size());
    }

    /**
     * Test remove one Numbered from the deck and check if the deck is still valid
     */
    @Test
    void testNumberCardStackPop() {
        numberCardStack.pop();
        assertEquals(47, numberCardStack.size());
    }

    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColorRed() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "RED";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }


    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColorBlue() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "BLUE";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }


    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColorGreen() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "GREEN";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }


    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColorWhite() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "WHITE";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }

    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColorPink() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "PINK";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }

    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColoCyan() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "CYAN";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }

    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColoYellow() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "YELLOW";
        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }

    /**
     * Test if the method checkCardColor() returns the right color
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void testCheckCardColoBlack() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "BLACK";

        boolean temp = (boolean) method.invoke(numberCardStack, methodArguments);
        assertTrue(temp);
    }

}