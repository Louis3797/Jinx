package org.jinx;

import org.jinx.game.GameController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        GameController gc = new GameController();
        gc.start();
    }
}