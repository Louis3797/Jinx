package org.jinx.presenter;

import org.jinx.model.IModel;
import org.jinx.player.AgentDifficulty;
import org.jinx.presenter.interfaces.ILoginPresenter;
import org.jinx.view.interfaces.ILoginView;
import org.jinx.view.interfaces.IView;

public class LoginPresenter implements ILoginPresenter {
    @Override
    public void login(String username, String password, AgentDifficulty difficulty) {

    }

    @Override
    public void showRegisterView() {

    }

    @Override
    public ILoginView getView() {
        return null;
    }

    @Override
    public void setView(IView<ILoginPresenter, ILoginView> view) {

    }

    @Override
    public IModel getModel() {
        return null;
    }

    @Override
    public void setModel(IModel model) {

    }
}
