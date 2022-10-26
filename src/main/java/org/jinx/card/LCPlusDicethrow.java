package org.jinx.card;

import org.jinx.dice.Dice;

public class LCPlusDicethrow extends LuckyCard{
    public LCPlusDicethrow() {
        super(LuckyCardNames.LCPlusDicethrow);
    }

    @Override
    public int effect() {
        int result = new Dice().use();

        System.out.println("Wuerfel: " + result);

        return result;
    }
}

