package org.jinx.presenter.interfaces;

import org.jinx.player.AgentDifficulty;
import org.jinx.view.interfaces.ILoginView;

public interface ILoginPresenter extends IPresenter<ILoginPresenter, ILoginView> {

    /**
     * player login
     *
     * @param username name of user
     *
     * @param password password of user
     *
     * @param difficulty if bot difficulty of bot
     */
    void login(String username, String password, AgentDifficulty difficulty);

    /**
     * shows register view
     */
    void showRegisterView();


}
