package org.jinx.player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

        @Test
        void testPlayer() {
            Player player = new Player("Test");
            assertEquals("Test", player.getName());
        }

        @Test
        void testPlayerHand() {
            Player player = new Player("Test");
            player.printHand();
        }

        @Test
        void testPlayerLuckyHand() {
            Player player = new Player("Test");
            player.printLuckyHand();
        }

}