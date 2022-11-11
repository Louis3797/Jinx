package org.jinx.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class GameControllerTest {
    @Test
    void testGameController() throws NoSuchMethodException {
        GameController gameController = new GameController();
        assertEquals(1, gameController.getClass().getMethod("startSequenz").getModifiers());

    }

}