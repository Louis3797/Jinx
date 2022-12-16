package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IGamehistoryPresenter;

public interface IGamehistoryView extends IView<IGamehistoryPresenter, IGamehistoryView> {

    /**
     * Inits view
     */
    void initComponents();

    /**
     * updates player label to current clicked playername
     *
     * @param username
     */
    void updatePlayerLabel(String username);

    /**
     * updates textpane with history of player
     *
     * @param histories of player
     */
    void updateHistory(String histories);
}
