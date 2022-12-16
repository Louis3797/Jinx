package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IRegisterView;

public interface IRegisterPresenter extends IPresenter<IRegisterPresenter, IRegisterView> {

    /**
     * register player to current manager
     *
     * @param username name of user
     * @param password password of user
     */
    void register(String username, String password);

    /**
     * shows login view
     */
    void showLoginView();
}
