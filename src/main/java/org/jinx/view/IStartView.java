package org.jinx.view;

import org.jinx.presenter.IStartPresenter;

public interface IStartView extends IView<IStartPresenter, IStartView> {

    /**
     * Inits View
     */
    void initComponents();
}
