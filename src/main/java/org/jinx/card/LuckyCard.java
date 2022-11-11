package org.jinx.card;

public abstract class LuckyCard extends Card {

    public LuckyCard(LuckyCardNames name) {
        super(name.name());
    }

    public abstract int effect() throws IllegalAccessException;

}
