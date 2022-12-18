package org.jinx.presenter;

import org.jinx.dice.Dice;
import org.jinx.view.DiceView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DicePresenterTest {

    Dice dice;
    DiceView diceView;
    DicePresenter dicePresenter;

    /**
     * sets up necessary classes
     */
    @BeforeEach
    void setup(){
        dice = new Dice();
        diceView = new DiceView();
        dicePresenter = new DicePresenter(diceView,dice);
    }

    /**
     * tests the set dicePosition of not updated dice
     */
    @Test
    void testNotUpdatedDice(){
        assertEquals(0,diceView.getCurrentDicePosition());
    }

    /**
     * tests if dicePosition >= 1 & <= 6 of updated dice
     */
    @Test
    void testUpdatedDice(){
        boolean updatedDice = false;
        dicePresenter.useDice();
        if(diceView.getCurrentDicePosition() >= 1 && diceView.getCurrentDicePosition() <= 6) updatedDice = true;
        assertTrue(updatedDice);
    }


}
