package org.jinx.card;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LC123 extends LuckyCard{
    public LC123(String name) {
        super(name);
    }

    @Override
    public int effect() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zahl von 4-6 eingeben");
        int number = scanner.nextInt();
        try{
            if (number < 4 || number > 6) {
                System.out.println("Nur zwischen 4-6");
                effect();
            }
        }
        catch (InputMismatchException e){
            effect();
        }
        return number;
    }
}
