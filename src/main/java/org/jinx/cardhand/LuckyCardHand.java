package org.jinx.cardhand;

import org.jinx.card.LuckyCard;
import org.jinx.card.LuckyCardNames;

public class LuckyCardHand extends CardHand<LuckyCard> {

    /**
     * Method checks if the player has a lucky card with the same given name in his hand
     *
     * @param cardName Given Lucky card name as enum
     * @return Returns true if he holds a Lucky card with the given name in his hand
     */
    public boolean has(LuckyCardNames cardName) {
        for (LuckyCard card : this) {
            if (card.getName().equals(cardName.name())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method counts how much Lucky cards the player holds in his hand of the given name
     *
     * @param cardName Given Lucky card name as enum
     * @return Returns the amount of Lucky cards with the same name as the given one
     */
    public int count(LuckyCardNames cardName) {
        int total = 0;
        for (LuckyCard card : this) {
            if (card.getName().equals(cardName.name())) {
                total++;
            }
        }

        return total;
    }

    @Override
    public void print() {
        LuckyCard.printFormatedLuckyCards(this);
    }
}
