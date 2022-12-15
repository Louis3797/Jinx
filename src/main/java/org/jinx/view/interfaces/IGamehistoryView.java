package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IGamehistoryPresenter;

public interface IGamehistoryView extends IView<IGamehistoryPresenter, IGamehistoryView> {

    /**
     * Inits view
     */
    void initComponents();

    /**
     * Displays error message on highscore text area if this method is called
     */
    void updateStatusTextAreaError();

}
