package org.jinx.history;

import org.jinx.cardhand.LuckyCardHand;
import org.jinx.cardhand.NumberCardHand;
import org.jinx.player.Player;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Record for the player history object
 *
 * @param name           Player name
 * @param cheated        If players uses Cheats = true
 * @param numberCardHand NumberCardHand of the Player
 * @param cardSum        Sum of the NumberCardhand
 * @param luckyCardHand  LuckyCardHand of Player
 * @param date           Date of the game
 * @param opponents      Opponents Infos
 */
public record PlayerHistory(String name, boolean cheated, NumberCardHand numberCardHand, int cardSum,
                            LuckyCardHand luckyCardHand, Date date,
                            List<Player> opponents) implements Serializable {

    @Override
    public String toString() {


        StringBuilder builder = new StringBuilder();

        for (Player opponent : opponents) {
            builder.append("-----\n").append("Spieler: ").append(opponent.getName()).append("\n").
                    append("Hat Cheats benutzt: ").append((opponent.isUsedCheats() ? "ja" : "nein")).append("\n").
                    append(opponent.getNumberCardHand().toString()).
                    append("\nKartenSumme: ").append(opponent.getPoints()).append("\n").append(opponent.getLuckyCardHand().toString()).append("-----\n").
                    append("+-".repeat(25));
        }

        return "\n----------\n" +
                date + "\nSpieler: " +
                name + "\nHat Cheats benutzt:" +
                (cheated ? "ja" : "nein") + "\n\n" +
                numberCardHand.toString() + "\nKartensumme: " +
                cardSum + "\n" +
                luckyCardHand.toString() + "\nGegenspieler Infos: \n" +
                builder;
    }
}
