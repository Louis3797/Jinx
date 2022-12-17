package org.jinx.presenter;

import org.jinx.game_state.GameState;
import org.jinx.view.StartView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StartPresenterTest {

    GameState gameState;
    StartView startView;
    StartPresenter startPresenter;


    @BeforeEach
    void setup(){
        gameState = new GameState();
        startView = new StartView();
        startPresenter = new StartPresenter(startView,gameState);
    }


    @Test
    void testDbManager(){
        startPresenter.setDBAsDataStorage();
        assertEquals("DatabaseLoginManager", gameState.getLoginManager().getClass().getSimpleName());
        assertEquals("DatabaseHistoryManager", gameState.getHistoryManager().getClass().getSimpleName());
    }

    @Test
    void testFileManager(){
        startPresenter.setTextFileAsDataStorage();
        assertEquals("FileLoginManager", gameState.getLoginManager().getClass().getSimpleName());
        assertEquals("FileHistoryManager", gameState.getHistoryManager().getClass().getSimpleName());
    }




}
