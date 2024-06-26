package org.jinx.cardstack;

import org.jinx.card.*;
import org.jinx.logging_file_handler.LogFileHandler;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

public class LuckyCardStack extends Stack<LuckyCard> implements Serializable {

    private transient final Logger logger = Logger.getLogger(NumberCardStack.class.getName());

    public static final long serialVersionUID = 42L;

    public LuckyCardStack() {
        logger.addHandler(LogFileHandler.getInstance().getFileHandler());
        logger.setUseParentHandlers(false);
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

        Path path = Path.of("LuckyCards.csv");

        boolean pathExist = Files.exists(path);

        // check if file doesn't exist
        if (!pathExist) {
            generateStandardDeck();
        }

        try {
            // Read file
            List<String> lines = Files.readAllLines(path);

            // Check if there are to few Cards in the file
            if (lines.size() != 12) {
                logger.info("There are too few or too many cards in the file");
                generateStandardDeck();
                return;
            }

            for (String cardName : lines) {


                boolean checkName =
                        cardName.equals(LuckyCardNames.LC123.name()) ||
                                cardName.equals(LuckyCardNames.LC456.name()) ||
                                cardName.equals(LuckyCardNames.LCMinus1.name()) ||
                                cardName.equals(LuckyCardNames.LCPlus1.name()) ||
                                cardName.equals(LuckyCardNames.LCPlusDicethrow.name()) ||
                                cardName.equals(LuckyCardNames.LCSum.name());


                // if color exists and card number range
                if (checkName) {
                    LuckyCard newCard = luckyFactory(LuckyCardNames.valueOf(cardName));
                    this.add(newCard);
                } else {
                    logger.info("Card Color is not supported in enum CardColor.");
                    generateStandardDeck();
                    return;
                }

            }


        } catch (IOException e) {
            logger.warning(e.getMessage());
            // Generate standard deck in error
            generateStandardDeck();
        }
    }

    private LuckyCard luckyFactory(LuckyCardNames name) {
        switch (name) {
            case LC123 -> {
                return new LC123();
            }
            case LC456 -> {
                return new LC456();
            }
            case LCMinus1 -> {
                return new LCMinus1();
            }
            case LCPlus1 -> {
                return new LCPlus1();
            }
            case LCPlusDicethrow -> {
                return new LCPlusDicethrow();
            }
            case LCSum -> {
                return new LCSum();
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Helper method that generates the deck without a csv file
     */
    private void generateStandardDeck() {
        // remove all old values
        this.removeAllElements();

        this.add(new LC123());
        this.add(new LC123());
        this.add(new LC456());
        this.add(new LC456());
        this.add(new LCMinus1());
        this.add(new LCMinus1());
        this.add(new LCPlus1());
        this.add(new LCPlus1());
        this.add(new LCPlusDicethrow());
        this.add(new LCPlusDicethrow());
        this.add(new LCSum());
        this.add(new LCSum());

        Collections.shuffle(this);
    }
}