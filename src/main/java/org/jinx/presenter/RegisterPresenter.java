package org.jinx.presenter;

import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IRegisterPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IRegisterView;


public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView view;
    private IModel model;

    public RegisterPresenter(IRegisterView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public IRegisterView getView() {
        return null;
    }

    @Override
    public void setView(IRegisterView view) {

    }

    @Override
    public IModel getModel() {
        return null;
    }

    @Override
    public void setModel(IModel model) {

    }

    @Override
    public void register(String username, String password) {

    }

    @Override
    public void showLoginView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Login.name());
    }
}