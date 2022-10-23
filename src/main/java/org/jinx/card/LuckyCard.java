package org.jinx.card;

public abstract class LuckyCard extends Card {

    public LuckyCard(String name) {
        super(name);
    }

    public abstract int effect();

    @Override
    public String toString() {
        return "|" + getName() + "|";
    }
}
