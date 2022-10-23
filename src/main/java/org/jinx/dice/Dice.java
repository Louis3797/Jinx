package org.jinx.dice;

/**
 * This Class is used to simulate a dice
 */
public class Dice {

    /**
     * Helper Method
     * Generates a Random number in range of 1-6
     *
     * @return Returnes a number in range from 1-6
     */
    private int generateRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * Returns the outcome of the dice roll
     *
     * @return Returns a number in range of 1 to 6
     */
    public int use() {
        return generateRandomNumber();
    }
}
