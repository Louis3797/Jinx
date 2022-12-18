package org.jinx.presenter;

import org.jinx.game.PlayerManager;
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
    public void shufflePlayer() {

    }
    public void updatePlayerLabel(String username){
        view.updateHistoryView(username);
    }

    @Override
    public void showHistoryView(){
        MainView.cardLayout.show(MainView.mainPanel, Views.History.name());
    }

    @Override
    public void showGameView() {
        if(((PlayerManager) model).getPlayers().size() >= 2){
            MainView.cardLayout.show(MainView.mainPanel, Views.Game.name());
        }
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


}
