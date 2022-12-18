package org.jinx;

import org.jinx.game.GameController;
import org.jinx.view.MainView;
import org.jinx.wrapper.SafeScanner;


public class Main {
    public static void main(String[] args) throws Exception {
        SafeScanner safeScanner = new SafeScanner();

        System.out.println("Spiel über GUI oder Konsole laufen lassen?\n[1] GUI\n[2] Konsole");
        if(safeScanner.nextIntInRange(1,2) == 1){
            new MainView();
        }
        else{
            GameController gc = new GameController();
            gc.start();
        }
    }
}