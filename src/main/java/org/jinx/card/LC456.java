package org.jinx.card;

import org.jinx.game.PlayerController;
import org.jinx.player.AutonomousPlayer;
import org.jinx.wrapper.SafeScanner;

public class LC456 extends LuckyCard {

    public LC456() {
        super(LuckyCardNames.LC456);
    }

    @Override
    public int effect() throws IllegalAccessException {

        PlayerController pc = PlayerController.getPlayerControllerInstance();

        System.out.println("Zahl von 4-6 eingeben");

        int number;

        if (pc.getCurrentPlayer().isHuman()) {
            number = new SafeScanner().nextIntInRange(1, 3);
        } else {
            number = ((AutonomousPlayer) pc.getCurrentPlayer()).getBestCardNumberForLCPickNumber(4, 6);
            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println(number);
        }

        return number;
    }
}
