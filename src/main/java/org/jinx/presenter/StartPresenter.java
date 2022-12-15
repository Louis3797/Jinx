package org.jinx.presenter;

import org.jinx.game_state.GameState;
import org.jinx.history.DatabaseHistoryManager;
import org.jinx.history.FileHistoryManager;
import org.jinx.login.DatabaseLoginManager;
import org.jinx.login.FileLoginManager;
import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IStartPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IStartView;

public class StartPresenter implements IStartPresenter {

    private IStartView view;
    private IModel model;

    public StartPresenter(IStartView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public IStartView getView() {
        return this.view;
    }

    @Override
    public void setView(IStartView view) {
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

    @Override
    public void startGame() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Login.name());
    }

    @Override
    public void showHighScoreView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.HighScore.name());
    }

    @Override
    public void setTextFileAsDataStorage() {
        ((GameState) model).setLoginManager(new FileLoginManager());
        ((GameState) model).setHistoryManager(new FileHistoryManager());

    }

    @Override
    public void setDBAsDataStorage() {
        ((GameState) model).setLoginManager(new DatabaseLoginManager());
        ((GameState) model).setHistoryManager(new DatabaseHistoryManager());

    }
}
