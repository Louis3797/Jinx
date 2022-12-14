package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IStartPresenter;

public interface IStartView extends IView<IStartPresenter, IStartView> {

    /**
     * Inits View
     */
    void initComponents();
}
