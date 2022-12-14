package org.jinx.presenter.interfaces;

import org.jinx.player.AgentDifficulty;
import org.jinx.view.interfaces.ILoginView;

public interface ILoginPresenter extends IPresenter<ILoginPresenter, ILoginView> {

    void login(String username, String password, AgentDifficulty difficulty);

    void showRegisterView();


}
