package org.jinx;

import org.jinx.game.GameController;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        GameController gc = new GameController();
        gc.start();
    }
}