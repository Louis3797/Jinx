package org.jinx.wrapper;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class SafeScannerTest {

    @Test
    void testNextStringSafe() {
        String input = "test";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        SafeScanner safeScanner = new SafeScanner();

        assertEquals(input, safeScanner.nextStringSafe());
    }

    @Test
    void testNextIntSafeWithIntegerInput() {
        Integer input = 1;
        InputStream in = new ByteArrayInputStream(Integer.toString(input).getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertEquals(input, safeScanner.nextIntSafe());
    }

    @Test
    void testNextIntInRangeWith1() {
        Integer input = 1;
        InputStream in = new ByteArrayInputStream(Integer.toString(input).getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertEquals(input, safeScanner.nextIntInRange(1,3));
    }

    @Test
    void testNextIntInRangeWith3() {
        Integer input = 1;
        InputStream in = new ByteArrayInputStream(Integer.toString(input).getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertEquals(input, safeScanner.nextIntInRange(1,3));
    }

    @Test
    void testNextYesOrNoAnswerYesWithY() {
        String input = "y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertTrue(safeScanner.nextYesNoAnswer());
    }

    @Test
    void testNextYesOrNoAnswerYesWithYes() {
        String input = "yes";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertTrue(safeScanner.nextYesNoAnswer());
    }

    @Test
    void testNextYesOrNoAnswerYesWithJa() {
        String input = "ja";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertTrue(safeScanner.nextYesNoAnswer());
    }

    @Test
    void testNextYesOrNoAnswerYesWithN() {
        String input = "n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertFalse(safeScanner.nextYesNoAnswer());
    }

    @Test
    void testNextYesOrNoAnswerYesWithNo() {
        String input = "no";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertFalse(safeScanner.nextYesNoAnswer());
    }

    @Test
    void testNextYesOrNoAnswerYesWithNein() {
        String input = "nein";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        SafeScanner safeScanner = new SafeScanner();

        assertFalse(safeScanner.nextYesNoAnswer());
    }
}