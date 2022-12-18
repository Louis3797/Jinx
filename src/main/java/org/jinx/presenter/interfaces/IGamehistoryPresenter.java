package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IGamehistoryView;

public interface IGamehistoryPresenter extends IPresenter<IGamehistoryPresenter, IGamehistoryView> {

    /**
     * loads login view
     */
    void showLoginView();

    /**
     * updates player label with logged in playername
     *
     * @param username playername
     */
    void updatePlayerLabel(String username);

    /**
     * sorts gamehistory by sum
     *
     * @param username name of chosen user
     */
    void loadGameHistoryBySum(String username);

    /**
     * sorts gamehistory by date
     *
     * @param username name of chosen user
     */
    void loadGameHistoryByDate(String username);
}
