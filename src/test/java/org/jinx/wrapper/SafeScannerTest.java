package org.jinx.wrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SafeScannerTest {
    @Test
    void testSafeScanner() {
        SafeScanner safeScanner = new SafeScanner();
        safeScanner.nextIntSafe();
        safeScanner.nextIntInRange(1, 2);
        safeScanner.nextStringSafe();
    }

}