package org.jinx.presenter;

import org.jinx.model.IModel;
import org.jinx.player.IPlayer;
import org.jinx.presenter.interfaces.IPlayerHandPresenter;
import org.jinx.view.interfaces.IPlayerHandView;

public class PlayerHandPresenter implements IPlayerHandPresenter {

    private IPlayerHandView view;
    private IPlayer model;

    public PlayerHandPresenter(IPlayerHandView view, IPlayer model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public IPlayerHandView getView() {
        return view;
    }

    @Override
    public void setView(IPlayerHandView view) {
        this.view = view;
    }

    @Override
    public IModel getModel() {
        return model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = (IPlayer) model;
    }
}
