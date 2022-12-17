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

    @BeforeAll
    static void regUser(){
        playerName = "Jan";
        password = "123";
        difficulty = AgentDifficulty.EASY;

        gameState = new GameState();
        gameState.getLoginManager().registerNewUser(playerName, password);

        playerManager = PlayerManager.getPlayerManagerInstance();
    }

    @BeforeEach
    void setup() {
        loginView = new LoginView();
        loginPresenter = new LoginPresenter(loginView, playerManager);
        playerManager.getPlayers().clear();
    }

    @Test
    void testStatusLabelSuccess() {
        loginPresenter.login(playerName, password, null);
        assertEquals("Sie haben sich erfolgreich eingeloggt.", loginView.getStatus().getText());
    }

    @Test
    void testStatusLabelErrorName() {
        loginPresenter.login(playerName + "fndj", password, null);
        assertEquals("Fehler bei der Anmeldung", loginView.getStatus().getText());
    }

    @Test
    void testStatusLabelErrorPassword() {
        loginPresenter.login(playerName, password + "fbnkd", null);
        assertEquals("Fehler bei der Anmeldung", loginView.getStatus().getText());
    }

    @Test
    void testStatusLabelErrorEmptyTextFieldName() {
        loginPresenter.login("", password, null);
        assertEquals("Bitte geben sie ihre Spielerdaten an um sich anzumelden!", loginView.getStatus().getText());
    }

    @Test
    void testStatusLabelErrorEmptyTextFieldPassword() {
        loginPresenter.login(playerName, "", null);
        assertEquals("Bitte geben sie ihre Spielerdaten an um sich anzumelden!", loginView.getStatus().getText());
    }

    @Test
    void testStatusLabelErrorEmptyTextFieldNameAndPassword() {
        loginPresenter.login("", "", null);
        assertEquals("Bitte geben sie ihre Spielerdaten an um sich anzumelden!", loginView.getStatus().getText());
    }

}
