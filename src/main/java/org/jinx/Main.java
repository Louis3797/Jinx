package org.jinx;

import org.jinx.game.GameController;
import org.jinx.view.MainView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        MainView mv = new MainView();
        GameController gc = new GameController();
        gc.start();
    }
}