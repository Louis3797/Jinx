package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IGamehistoryView;

public interface IGamehistoryPresenter extends IPresenter<IGamehistoryPresenter, IGamehistoryView> {

    void showLoginView();

    void updatePlayerLabel(String username);

    void loadGameHistory(String username);
}
