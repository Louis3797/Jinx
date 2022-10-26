package org.jinx.card;

import org.jinx.wrapper.SafeScanner;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LC123 extends LuckyCard{
    public LC123() {
        super(LuckyCardNames.LC123);
    }

    @Override
    public int effect() {
        System.out.println("Zahl von 1-3 eingeben");

        return new SafeScanner().nextIntInRange(1,3);
    }
}
