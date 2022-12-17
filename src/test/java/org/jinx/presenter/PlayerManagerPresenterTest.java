package org.jinx.presenter;

import org.jinx.game.PlayerManager;
import org.jinx.view.LoginView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerManagerPresenterTest {

    String username1 = "Jan";
    String username2 = "Kerim";
    String username3 = "Louis";
    String username4 = "Freddy";

    String password = "123";

    static PlayerManager playerManager;
    static LoginView loginView;
    static LoginPresenter loginPresenter;


    @BeforeAll
    static void setup(){
        playerManager = PlayerManager.getPlayerManagerInstance();
        loginView = new LoginView();
        loginPresenter = new LoginPresenter(loginView, playerManager);
    }

    @AfterEach
    void clearPlayers(){
        playerManager.getPlayers().clear();
    }

    @Test
    void testUpdatePlayerLabelOne() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password, null);

        assertEquals(username1, loginView.getPlayerManagerView().getLabels()[0].getText());
    }

    @Test
    void testUpdatePlayerLabelTwo() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password, null);

        loginView.setUsernameField(username2);
        loginPresenter.login(username2, password, null);

        assertEquals(username2, loginView.getPlayerManagerView().getLabels()[1].getText());
    }

    @Test
    void testUpdatePlayerLabelThree() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password, null);

        loginView.setUsernameField(username2);
        loginPresenter.login(username2, password, null);

        loginView.setUsernameField(username3);
        loginPresenter.login(username3, password, null);

        assertEquals(username3, loginView.getPlayerManagerView().getLabels()[2].getText());
    }

    @Test
    void testUpdatePlayerLabelFour() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password, null);

        loginView.setUsernameField(username2);
        loginPresenter.login(username2, password, null);

        loginView.setUsernameField(username3);
        loginPresenter.login(username3, password, null);

        loginView.setUsernameField(username4);
        loginPresenter.login(username4, password, null);

        assertEquals(username4, loginView.getPlayerManagerView().getLabels()[3].getText());
    }


}
