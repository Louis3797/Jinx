package org.jinx.cardstack;

import org.jinx.card.CardColor;
import org.jinx.card.NumberCard;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

public class NumberCardStack extends Stack<NumberCard> implements Serializable {

    private transient final Logger LOGGER = Logger.getLogger(NumberCardStack.class.getName());

    public static final long serialVersionUID = 42L;

    public NumberCardStack() {
        generateDeck();
    }

    /**
     * Generates the Deck
     */
    private void generateDeck() {

        // cases:
        // 1. file is not there -> check
        // 2. too few or to many cards in file
        // 3. card colors in file are nor supported from enum
        // 4. Number of card is not in range of 1-6
        // 5. Check if a card does only exists once
        // 6. IOException
        // 7. all good

        Path path = Path.of("NumberCards.csv");

        boolean pathExist = Files.exists(path);

        // check if file doesn't exist
        if (!pathExist) {
            generateStandardDeck();
        }

        try {
            // Read file
            List<String> lines = Files.readAllLines(path);

            // Check if there are to few Cards in the file
            if (lines.size() != 48) {
                LOGGER.info("There are too few or too many cards in the file");
                generateStandardDeck();
                return;
            }

            // check color
            for (String i : lines) {

                String[] data = i.split(",");

                String cardColor = data[1].toUpperCase();
                boolean doesColorExists = checkCardColor(cardColor);

                int cardNumber = Integer.parseInt(data[0]);
                boolean checkNumber = cardNumber >= 1 && cardNumber <= 6;

                // if color exists and card number range
                if (doesColorExists && checkNumber) {
                    NumberCard newCard = new NumberCard(data[0], CardColor.valueOf(cardColor));
                    if(checkIfCardExists(newCard)) {
                        LOGGER.info("NumberCard.csv contains multiple instances of the same NumberCard");
                        generateStandardDeck();
                        return;
                    }
                    this.add(newCard);
                } else {
                    LOGGER.info("Card Color is not supported in enum CardColor.");
                    generateStandardDeck();
                    return;
                }

            }


        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
            // Generate standard deck in error
            generateStandardDeck();
        }
    }

    /**
     * Checks if the given Card does exist in the Stack
     * @param newCard Given NumberCard we want to check
     * @return Return true if given Card is in Stack
     */
    private boolean checkIfCardExists(NumberCard newCard) {

        for(NumberCard card : this){
            if (card.getName().equals(newCard.getName()) && card.getColor().equals(newCard.getColor())){
                return true;
            }
        }

        return false;
    }

    /**
     * Helper method that generates the deck without a csv file
     */
    private void generateStandardDeck() {
        // remove all old values
        this.removeAllElements();

        // generate
        for (CardColor color : CardColor.values()) {
            for (int i = 1; i < 7; i++) {
                this.add(new NumberCard(Integer.toString(i), color));
            }
        }

        Collections.shuffle(this);
    }

    /**
     * Helper funtion that checks if the card color from a file is present in CardColor enum
     *
     * @param colorName Name of the color
     * @return Returns true if the color is present in CardColor
     */
    private boolean checkCardColor(String colorName) {

        for (CardColor cardColor : CardColor.values()) {
            if (cardColor.name().equals(colorName)) {
                return true;
            }
        }

        return false;
    }
}