package org.jinx.card;

import java.io.Serializable;

public abstract class Card implements Serializable {

    private final String name;

    public Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
