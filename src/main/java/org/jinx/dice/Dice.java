package org.jinx.dice;

/**
 * This Class is used to simulate a dice
 */
public class Dice implements IDice {


    private int currentDicePosition = 6;

    /**
     * Returns the outcome of the dice roll
     *
     * @return Returns a number in range of 1 to 6
     */
    @Override
    public int use() {
        this.currentDicePosition = generateRandomNumber();
        return this.currentDicePosition;
    }

    @Override
    public int getCurrentDicePosition() {
        return this.currentDicePosition;
    }
}
