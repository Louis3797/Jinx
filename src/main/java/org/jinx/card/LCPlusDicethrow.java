package org.jinx.card;

public class LCPlusDicethrow extends LuckyCard{
    public LCPlusDicethrow(String name) {
        super(name);
    }

    @Override
    public int effect() {
        int result = (int) (Math.random() * 6 + 1);

        System.out.println("Wuerfel: " + result);

        return result;
    }
}

