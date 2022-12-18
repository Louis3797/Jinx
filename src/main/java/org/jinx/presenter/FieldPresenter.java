package org.jinx.presenter;

import org.jinx.dice.IDice;
import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IFieldPresenter;
import org.jinx.view.interfaces.IFieldView;

public class FieldPresenter implements IFieldPresenter {

    IFieldView view;
    IDice model;

    public FieldPresenter(IFieldView view, IModel model) {
        this.view = view;
        this.model = (IDice) model;
        view.setPresenter(this);
    }


    @Override
    public IFieldView getView() {
        return this.view;
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = (IDice) model;
    }

    @Override
    public void setView(IFieldView view) {
        this.view = view;
    }
}
