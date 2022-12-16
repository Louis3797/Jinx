package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IPlayerManagerPresenter;

public interface IPlayerManagerView extends IView<IPlayerManagerPresenter, IPlayerManagerView> {

    /**
     * Inits view
     */
    void initComponents();

}
