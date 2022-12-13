package org.jinx.presenter;

import org.jinx.model.IModel;
import org.jinx.view.ILoginView;
import org.jinx.view.IView;

public class LoginPresenter implements ILoginPresenter{

    private IView view;
    private IModel model;

    public LoginPresenter(IView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public ILoginView getView() {
        return null;
    }

    @Override
    public void setView(IView<ILoginPresenter, ILoginView> view) {

    }

    @Override
    public IModel getModel() {
        return null;
    }

    @Override
    public void setModel(IModel model) {

    }
}
