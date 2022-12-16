package org.jinx.presenter;

import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IGamehistoryPresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IGamehistoryView;


public class GamehistoryPresenter implements IGamehistoryPresenter {

    private IGamehistoryView view;
    private IModel model;


    public GamehistoryPresenter(IGamehistoryView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public IGamehistoryView getView() {

        return this.view;
    }

    @Override
    public void setView(IGamehistoryView view) {
        this.view = view;
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void showLoginView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Login.name());
    }
}
