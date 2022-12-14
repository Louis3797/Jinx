package org.jinx.view;

import org.jinx.presenter.ILoginPresenter;

public interface ILoginView extends IView<ILoginPresenter, ILoginView> {

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
