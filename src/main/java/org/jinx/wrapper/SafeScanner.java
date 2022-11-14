package org.jinx.wrapper;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * SafeScanner is a wrapper class around the java.util.Scanner that provides predefined methodes
 * for a safe use of the Scanner class in our program
 */
public class SafeScanner {

    private final Scanner scanner;

    /**
     * Standard Constructor
     */
    public SafeScanner() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Wrapper method for the next method of java.util.Scanner
     *
     * @return Returns a String that the user inputs
     */
    public String nextStringSafe() {
        return scanner.next();
    }

    /**
     * Wrapper method for the nextInt method of java.util.Scanner
     *
     * @return Returns an integer that the user inputs
     */
    public int nextIntSafe() {
        int response;

        try {
            response = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("You did not enter an integer.");
            scanner.next();
            return nextIntSafe();
        }

        return response;
    }

    /**
     * Method asks the user to input an integer between the given min and max value
     * and handles upcoming errors - Uses nextIntSafe internally
     *
     * @param min min value of the given range that the user can input
     * @param max max value of the given range that the user can input
     * @return Returns the integer that the user inputs and that is in range of the min and max value
     */
    public int nextIntInRange(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("Given min value needs to be greater than the given max value");
        }

        int response = nextIntSafe();
        while (response < min || response > max) {
            System.out.println("Number must be between " + min + " and " + max);
            response = nextIntSafe();
        }

        return response;
    }

    /**
     * Method asks the user to input y,yes,ja or n,no,nein
     *
     * @return Returns true if user inputs y,yes,ja or false if user inputs n,no,nein
     */
    public boolean nextYesNoAnswer() {
        String response = scanner.next();

        if (response.matches("^(y|yes|ja)$")) {
            return true;
        } else if (response.matches("^(n|no|nein)$")) {
            return false;
        } else {
            System.out.println("Yes/no response required. (y/n, yes/no, ja,nein)");
            return nextYesNoAnswer();
        }
    }
}
