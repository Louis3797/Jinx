package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IPresenter;

/**
 * Standard Generic View Interface
 *
 * All View Interfaces should inherit from this interface
 *
 * @param <P> Presenter Type
 * @param <V> View Type
 */
public interface IView<P extends IPresenter<P, V>, V extends IView<P, V>> {

    /**
     * Inits view
     */
    void initComponents();

    /**
     * Sets Presenter in View
     *
     * @param presenter Presenter that should communicate with the view
     */
    void setPresenter(P presenter);
}