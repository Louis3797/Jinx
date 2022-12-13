package org.jinx;

import org.jinx.game.GameController;
import org.jinx.view.MainFrame;

public class Main {
    public static void main(String[] args) throws Exception {

        MainFrame mf = new MainFrame();

        GameController gc = new GameController();
        gc.start();
    }
}