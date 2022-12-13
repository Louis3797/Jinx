package org.jinx.view;

import org.jinx.presenter.ILoginPresenter;

public interface ILoginView extends IView<ILoginPresenter, ILoginView> {

    void createView();
}
