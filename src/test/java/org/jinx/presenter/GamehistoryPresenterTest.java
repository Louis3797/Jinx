package org.jinx.presenter;

import org.jinx.view.PlayerManagerView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GamehistoryPresenterTest {

    PlayerManagerView playerManagerView;

    String playerName;

    /**
     * sets up necessary classes and variables
     */
    @BeforeEach
    void setup(){
        playerManagerView = new PlayerManagerView();
        playerName = "Test";
    }

    /**
     * tests playerName label for same name
     */
    @Test
    void testUpdatePlayerLabel(){
        playerManagerView.updateHistoryView(playerName);
        assertEquals(playerName, playerManagerView.getGamehistoryView().getPlayerName().getText());
    }

    /**
     * tests playerName label for different name
     */
    @Test
    void testUpdatePlayerLabelError(){
        playerManagerView.updateHistoryView(playerName);
        assertNotEquals("test", playerManagerView.getGamehistoryView().getPlayerName().getText());
    }

}
