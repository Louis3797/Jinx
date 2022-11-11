package org.jinx.cardstack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BooleanSupplier;

class NumberCardStackTest {

    @Test
    void testNumberCardStack()  {
        var numberCardStack = new NumberCardStack();
        assertEquals(48, numberCardStack.size());
    }

    @Test
    void testNumberCardStack2()  {
        var numberCardStack = new NumberCardStack();
        numberCardStack.remove(1);
        assertEquals(47, numberCardStack.size());
    }

}