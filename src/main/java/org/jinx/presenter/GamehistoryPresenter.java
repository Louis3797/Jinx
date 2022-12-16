package org.jinx.presenter;

import org.jinx.game.PlayerManager;
import org.jinx.game_state.GameState;
import org.jinx.history.IHistoryManager;
import org.jinx.history.PlayerHistory;
import org.jinx.model.IModel;
import org.jinx.player.Player;
import org.jinx.presenter.interfaces.IGamehistoryPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IGamehistoryView;

import java.util.ArrayList;
import java.util.List;


public class GamehistoryPresenter implements IGamehistoryPresenter {

    private IGamehistoryView view;
    private IModel model;

    private final PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    public GamehistoryPresenter(IGamehistoryView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    public void loadGamehistory(String username) {
        for (Player player : playerManager.getPlayers()) {

            if (player.getName().equals(username)) {

                StringBuilder allHistories = new StringBuilder();

                for (PlayerHistory playerHistory : MainView.gameState.getHistoryManager().getHistory(player)) {
                    allHistories.append(playerHistory.toString());
                }
                view.updateHistory(allHistories.toString());
            }
        }
    }


    @Override
    public void updatePlayerLabel(String username) {
        view.updatePlayerLabel(username);
    }

    @Override
    public void showLoginView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Login.name());
    }

    @Override
    public IGamehistoryView getView() {
        return this.view;
    }

    @Override
    public void setView(IGamehistoryView view) {
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
