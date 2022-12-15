package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IRegisterPresenter;

public interface IRegisterView extends IView<IRegisterPresenter, IRegisterView> {

    /**
     * Inits view
     */
    void initComponents();

    /**
     * Displays success message on status label if this method is called
     */
    void updateStatusLabelSuccess();

    /**
     * Displays error message on status label if this method is called
     */
    void updateStatusLabelError();

    /**
     * Displays error message on status label if this method is called
     */
    void updateStatusLabelErrorEmptyTextField();
}
