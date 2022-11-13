package org.jinx.cardstack;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class NumberCardStackTest {
    /**
     * Test generateDeck() method
     */
    @Test
    void testNumberCardStack() {
        var numberCardStack = new NumberCardStack();
        assertEquals(48, numberCardStack.size());
    }

    /**
     * Test remove one Numbercard from the deck and check if the deck is still valid
     */
    @Test
    void testNumberCardStack2() {
        var numberCardStack = new NumberCardStack();
        numberCardStack.remove(1);
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
    void testcheckCardColor() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "RED";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColor2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "BLUE";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColor3() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "GREEN";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColor4() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "WHITE";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColor5() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "PINK";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColo6() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "CYAN";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColo7() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "YELLOW";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
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
    void testcheckCardColo8() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var numberCardStack = new NumberCardStack();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = numberCardStack.getClass().getDeclaredMethod("checkCardColor", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "BLACK";
        boolean temp = (boolean) method.invoke(numberCardStack, medthodArgruments);
        assertTrue(temp);
    }

}