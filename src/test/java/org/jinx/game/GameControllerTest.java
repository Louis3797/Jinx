package org.jinx.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class GameControllerTest {
    @Test
    void testGameController() throws NoSuchMethodException {
        var gameController = new GameController();
        gameController.start();

    }

}