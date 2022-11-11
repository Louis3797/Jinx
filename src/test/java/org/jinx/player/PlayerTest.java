package org.jinx.player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

        @Test
        void testPlayerHand() {
            var player = new Player("Test");
            player.printHand();
            //assertEquals(0, player.getHandSize());
        }

        @Test
        void testPlayerLuckyHand() {
            var player = new Player("Test");
            player.printLuckyHand();
        }

}