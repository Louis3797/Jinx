package org.jinx.presenter.interfaces;

import org.jinx.model.IModel;
import org.jinx.view.interfaces.IView;

/**
 * Standard Generic Presenter Interface
 * <p>
 * All Presenter Interfaces should inherit from this interface
 *
 * @param <P> Presenter Type
 * @param <V> View Type
 */
public interface IPresenter<P extends IPresenter<P, V>, V extends IView<P, V>> {

    /**
     * Returns the current view
     *
     * @return Returns the current view
     */
    V getView();

    /**
     * Sets View of the Presenter
     *
     * @param view View
     */
    void setView(IView<P, V> view);

    /**
     * Returns the Model Object of the Presenter
     *
     * @return Returns the Model the presenter uses
     */
    IModel getModel();

    /**
     * Sets the Model of the Presenter
     *
     * @param model Model
     */
    void setModel(IModel model);
}
