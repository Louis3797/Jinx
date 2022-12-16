package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IPlayerManagerPresenter;

public interface IPlayerManagerView extends IView<IPlayerManagerPresenter, IPlayerManagerView> {

    /**
     * Inits view
     */
    void initComponents();

    /**
     * updates jlabel with username
     *
     * @param username name of user
     */
    void updateLabel(String username);

    /**
     * updates the history view with current playername
     *
     * @param username of current player
     */
    void updateHistoryView(String username);
}
