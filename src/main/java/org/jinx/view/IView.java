package org.jinx.view;

import org.jinx.presenter.IPresenter;

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
     * Sets Presenter in View
     *
     * @param presenter Presenter that should communicate with the view
     */
    void setPresenter(IPresenter<P, V> presenter);
}