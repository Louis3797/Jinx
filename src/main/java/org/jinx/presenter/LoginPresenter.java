package org.jinx.presenter;

import org.jinx.model.IModel;
import org.jinx.player.AgentDifficulty;
import org.jinx.presenter.interfaces.ILoginPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.ILoginView;
import org.jinx.view.interfaces.IView;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;
    private IModel model;

    public LoginPresenter(ILoginView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public void login(String username, String password, AgentDifficulty difficulty) {
        if (MainView.gameState.getLoginManager().checkCredentials(username, password)) {
            view.updateStatusLabelSuccess();
        }
        else if (username.equals("") || password.equals("")) {
            view.updateStatusLabelErrorEmptyTextField();
        }
        else {
            view.updateStatusLabelError();
        }
    }

    @Override
    public void showRegisterView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Register.name());
    }

    @Override
    public ILoginView getView() {
        return this.view;
    }

    @Override
    public void setView(ILoginView view) {
        this.view = view;
    }


    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }
}