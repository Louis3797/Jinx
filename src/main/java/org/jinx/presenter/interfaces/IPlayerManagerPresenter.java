package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IPlayerManagerView;

public interface IPlayerManagerPresenter extends IPresenter<IPlayerManagerPresenter, IPlayerManagerView> {

    /**
     * shuffles player order
     */
    void shufflePlayer();

    /**
     * shows game view
     */
    void showGameView();

    /**
     * shows history view
     */
    void showHistoryView();

    /**
     * updates logged in players
     *
     * @param username logged in playername
     */
    void updatePlayerLabel(String username);

}
