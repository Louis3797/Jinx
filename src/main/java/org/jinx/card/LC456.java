package org.jinx.card;

import java.util.Scanner;


public class LC456 extends LuckyCard {

    public LC456(String name) {
        super(name);
    }

    @Override
    public int effect() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zahl von 4-6 eingeben");
        int number = scanner.nextInt();

        if (number < 4 || number > 6) {
            System.out.println("Nur zwischen 4-6");
            return effect();
        }

        return number;
    }
}
