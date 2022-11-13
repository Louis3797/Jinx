package org.jinx.card;

import org.jinx.game.PlayerController;
import org.jinx.player.AutonomousPlayer;
import org.jinx.wrapper.SafeScanner;

public class LC123 extends LuckyCard {
    public LC123() {
        super(LuckyCardNames.LC123);
    }

    @Override
    public int effect() throws IllegalAccessException {

        PlayerController pc = PlayerController.getPlayerControllerInstance();

        System.out.println("Zahl von 1-3 eingeben");

        int number;

        if (pc.getCurrentPlayer().isHuman()) {
            number = new SafeScanner().nextIntInRange(1, 3);
        } else {
            number = ((AutonomousPlayer) pc.getCurrentPlayer()).getBestCardNumberForLCPickNumber(1, 3);
            // This line is only here for cosmetic reasons
            // to bring the human player a better game experience
            // by pretending that the bot can also write to the console.
            System.out.println(number);
        }

        return number;
    }
}
