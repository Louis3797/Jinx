package org.jinx.card;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LC123 extends LuckyCard{
    public LC123() {
        super(LuckyCardNames.LC123);
    }

    @Override
    public int effect() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zahl von 1-3 eingeben");
        int number = scanner.nextInt();

        if (number < 1 || number > 3) {
            System.out.println("Nur zwischen 1-3");
            return effect();
        }

        return number;
    }
}
