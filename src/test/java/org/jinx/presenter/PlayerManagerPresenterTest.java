package org.jinx.presenter;

import org.jinx.game.PlayerManager;
import org.jinx.view.LoginView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerManagerPresenterTest {

    //sets up necessary variables and classes
    String username1 = "Jan";
    String username2 = "Kerim";
    String username3 = "Louis";
    String username4 = "Freddy";

    String password = "123";

    PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    LoginView loginView = new LoginView();
    LoginPresenter loginPresenter = new LoginPresenter(loginView, playerManager);

    /**
     * clears all players before every test
     */
    @BeforeEach
    void clearPlayers(){
        playerManager.getPlayers().clear();
    }


    /**
     * tests the first label if login
     * is successful
     */
    @Test
    void testUpdatePlayerLabelOne() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password, null);

        assertEquals(username1, loginView.getPlayerManagerView().getLabels()[0].getText());
    }


    /**
     * tests the second label if login
     * is successful
     */
    @Test
    void testUpdatePlayerLabelTwo() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password, null);

        loginView.setUsernameField(username2);
        loginPresenter.login(username2, password, null);

        assertEquals(username2, loginView.getPlayerManagerView().getLabels()[1].getText());
    }


    /**
     * tests the third label if login
     * is successful
     */
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


    /**
     * tests the fourth label if login
     * is successful
     */
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

    /**
     * tests label if login is
     * unsuccessful
     */
    @Test
    void testUpdatePlayerLabelOneFailedLogin() {
        loginView.setUsernameField(username1);
        loginPresenter.login(username1, password+"gnd", null);

        assertEquals("", loginView.getPlayerManagerView().getLabels()[0].getText());
    }


}
