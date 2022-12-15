package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IRegisterView;

public interface IRegisterPresenter extends IPresenter<IRegisterPresenter, IRegisterView> {

    void register(String username, String password);

    void showLoginView();
}
