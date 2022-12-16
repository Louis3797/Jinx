package org.jinx.view.interfaces;

import org.jinx.history.PlayerHistory;
import org.jinx.presenter.interfaces.IGamehistoryPresenter;

import java.util.List;

public interface IGamehistoryView extends IView<IGamehistoryPresenter, IGamehistoryView> {

    /**
     * Inits view
     */
    void initComponents();

    void updatePlayerLabel(String username);

    void updateHistory(String histories);
}
