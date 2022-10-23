package org.jinx;

import org.jinx.game.GameController;

import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        LOGGER.info("Start Game...");

        GameController gc = new GameController();
        gc.start();
    }
}