package org.jinx.card;

import org.jinx.wrapper.SafeScanner;

public class LC456 extends LuckyCard {

    public LC456() {
        super(LuckyCardNames.LC456);
    }

    @Override
    public int effect() {
        System.out.println("Zahl von 4-6 eingeben");

        return new SafeScanner().nextIntInRange(4, 6);
    }
}
