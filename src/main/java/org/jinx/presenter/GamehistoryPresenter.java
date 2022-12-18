package org.jinx.presenter;

import org.jinx.game.PlayerManager;
import org.jinx.history.PlayerHistory;
import org.jinx.model.IModel;
import org.jinx.player.Player;
import org.jinx.presenter.interfaces.IGamehistoryPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IGamehistoryView;

import java.util.ArrayList;


public class GamehistoryPresenter implements IGamehistoryPresenter {

    private IGamehistoryView view;
    private IModel model;

    private final PlayerManager playerManager = PlayerManager.getPlayerManagerInstance();

    public GamehistoryPresenter(IGamehistoryView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    public void loadGameHistoryBySum(String username) {
        for (Player player : playerManager.getPlayers()) {

            if (player.getName().equals(username)) {

                ArrayList<PlayerHistory> histories = new ArrayList<>(MainView.gameState.getHistoryManager().getHistory(player));

                if (histories.size() == 0) {
                    view.updateHistory("");
                } else {
                    histories.sort((o1, o2) -> o2.cardSum() - o1.cardSum());

                    view.updateHistory(histories.toString());
                }
            }
        }
    }

    public void loadGameHistoryByDate(String username) {
        for (Player player : playerManager.getPlayers()) {

            if (player.getName().equals(username)) {

                ArrayList<PlayerHistory> histories = new ArrayList<>(MainView.gameState.getHistoryManager().getHistory(player));
                if (histories.size() == 0) {
                    view.updateHistory("");
                } else {
                    view.updateHistory(histories.toString());
                }

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
