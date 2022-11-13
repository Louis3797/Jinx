package org.jinx.player;
import org.jinx.card.LuckyCardNames;
import org.jinx.game.PlayerController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

        /**
         * Test if the methpd hasLuckyCard returns false if the player has no lucky cards
         */
        @Test
        void testPlayerHand() {
            var player = new Player("Test");
            assertEquals(false,player.hasLuckyCard(LuckyCardNames.LC123));
        }

}