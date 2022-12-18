package org.jinx.dice;

import org.jinx.model.IModel;

public interface IDice extends IModel {
    /**
     * Helper Method
     * Generates a Random number in range of 1-6
     *
     * @return Returnes a number in range from 1-6
     */
    default int generateRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * uses the dice
     * @return
     */
    int use();

    /**
     * gets current dice position
     * @return dice value
     */
    int getCurrentDicePosition();
}
