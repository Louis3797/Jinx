package org.jinx.presenter;

import org.jinx.game.PlayerManager;
import org.jinx.game_state.GameState;
import org.jinx.player.AgentDifficulty;
import org.jinx.view.LoginView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPresenterTest {

    static String playerName;
    static String password;
    static AgentDifficulty difficulty;
    static GameState gameState;
    static PlayerManager playerManager;
    LoginView loginView;
    LoginPresenter loginPresenter;

    /**
     * sets up necessary variables
     */
    @BeforeAll
    static void regUser(){
        playerName = "Jan";
        password = "123";
        difficulty = AgentDifficulty.EASY;

        gameState = new GameState();
        gameState.getLoginManager().registerNewUser(playerName, password);

        playerManager = PlayerManager.getPlayerManagerInstance();
    }

    /**
     * sets up necessary classes and clears all players
     */
    @BeforeEach
    void setup() {
        loginView = new LoginView();
        loginPresenter = new LoginPresenter(loginView, playerManager);
        playerManager.getPlayers().clear();
    }

    /**
     * test label if login is successful
     */
    @Test
    void testStatusLabelSuccess() {
        loginPresenter.login(playerName, password, null);
        assertEquals("Sie haben sich erfolgreich eingeloggt.", loginView.getStatus().getText());
    }

    /**
     * tests label if name is non existent
     */
    @Test
    void testStatusLabelErrorName() {
        loginPresenter.login(playerName + "fndj", password, null);
        assertEquals("Fehler bei der Anmeldung", loginView.getStatus().getText());
    }

    /**
     * tests label if password is wrong
     */
    @Test
    void testStatusLabelErrorPassword() {
        loginPresenter.login(playerName, password + "fbnkd", null);
        assertEquals("Fehler bei der Anmeldung", loginView.getStatus().getText());
    }

    /**
     * tests label if name is empty
     */
    @Test
    void testStatusLabelErrorEmptyTextFieldName() {
        loginPresenter.login("", password, null);
        assertEquals("Bitte geben sie ihre Spielerdaten an um sich anzumelden!", loginView.getStatus().getText());
    }

    /**
     * tests label if password is empty
     */
    @Test
    void testStatusLabelErrorEmptyTextFieldPassword() {
        loginPresenter.login(playerName, "", null);
        assertEquals("Bitte geben sie ihre Spielerdaten an um sich anzumelden!", loginView.getStatus().getText());
    }

    /**
     * tests label if name and password are empty
     */
    @Test
    void testStatusLabelErrorEmptyTextFieldNameAndPassword() {
        loginPresenter.login("", "", null);
        assertEquals("Bitte geben sie ihre Spielerdaten an um sich anzumelden!", loginView.getStatus().getText());
    }

}
