package org.jinx.presenter;

import org.jinx.encryption.AES;
import org.jinx.game_state.GameState;
import org.jinx.view.RegisterView;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RegisterPresenterTest {

    GameState gameState;
    RegisterView registerView;

    static RegisterPresenter registerPresenter;
    static List<String> user;

    /**
     * stores registered users in list
     * @throws IOException fileException
     */
    @BeforeAll
    static void savesInList() throws IOException {
        user = Files.readAllLines(Path.of("Login.txt"));
    }

    /**
     * clears txt and sets necessary classes
     * @throws IOException fileException
     */
    @BeforeEach
    void setup() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Login.txt"));
        writer.close();

        gameState = new GameState();
        registerView = new RegisterView();
        registerPresenter = new RegisterPresenter(registerView,gameState);
    }

    /**
     * tests label after successful register
     */
    @Test
    void testUpdateStatusLabelSuccess(){
        registerPresenter.register("Jan","123");
        assertEquals("Spieler wurde erfolgreich erstellt. Loggen sie sich nun ein.",registerView.getStatus().getText());
    }

    /**
     * tests label after player already exists
     */
    @Test
    void testUpdateStatusLabelError(){
        registerPresenter.register("Jan","123");
        registerPresenter.register("Jan","456");
        assertEquals("Fehler bei der Registrierung Spieler existiert schon!",registerView.getStatus().getText());
    }

    /**
     * tests label after failed register
     * because username is empty
     */
    @Test
    void testUpdateStatusLabelErrorEmptyTextFieldName(){
        registerPresenter.register("","123");
        assertEquals("Bitte füllen sie die Textfelder aus",registerView.getStatus().getText());
    }

    /**
     * tests label after failed register
     * because password is empty
     */
    @Test
    void testUpdateStatusLabelErrorEmptyTextFieldPassword(){
        registerPresenter.register("Jan","");
        assertEquals("Bitte füllen sie die Textfelder aus",registerView.getStatus().getText());
    }

    /**
     * tests label after failed register
     * because username and password are empty
     */
    @Test
    void testUpdateStatusLabelErrorEmptyTextFieldNameAndPassword(){
        registerPresenter.register("","");
        assertEquals("Bitte füllen sie die Textfelder aus",registerView.getStatus().getText());
    }

    /**
     * re-registers all players
     */
    @AfterAll
    static void reRegPlayers(){
        for(String c : user){
            String[] data = c.split(",");
            String dec = AES.decrypt(data[1], "secretKey");
            registerPresenter.register(data[0],dec);
        }
    }
}
