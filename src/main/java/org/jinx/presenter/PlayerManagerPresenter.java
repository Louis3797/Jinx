package org.jinx.presenter;

import org.jinx.Main;
import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IPlayerManagerPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IPlayerManagerView;

public class PlayerManagerPresenter implements IPlayerManagerPresenter {

    private IPlayerManagerView view;
    private IModel model;


    public PlayerManagerPresenter(IPlayerManagerView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }


    @Override
    public IPlayerManagerView getView() {
        return this.view;
    }

    @Override
    public void setView(IPlayerManagerView view) {
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
    public void updatePlayerList() {

    }

    @Override
    public void shufflePlayer() {

    }

    @Override
    public void showGameView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Game.name());
    }
}
