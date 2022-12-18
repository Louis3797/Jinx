package org.jinx.presenter;

import org.jinx.dice.IDice;
import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IDicePresenter;
import org.jinx.view.interfaces.IDiceView;

public class DicePresenter implements IDicePresenter {

    IDiceView view;
    IDice model;

    public DicePresenter(IDiceView view, IModel model) {
        this.view = view;
        this.model = (IDice) model;
        view.setPresenter(this);
    }

    @Override
    public void useDice() {
        int result = model.use();
        updateView();
    }

    @Override
    public void updateView() {
        view.updateDice(model.getCurrentDicePosition());
    }

    @Override
    public IDiceView getView() {
        return this.view;
    }

    @Override
    public void setView(IDiceView view) {
        this.view = view;
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = (IDice) model;
    }
}
