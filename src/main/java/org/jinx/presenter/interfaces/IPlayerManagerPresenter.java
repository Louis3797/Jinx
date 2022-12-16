package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IPlayerManagerView;

public interface IPlayerManagerPresenter extends IPresenter<IPlayerManagerPresenter, IPlayerManagerView> {


    void updatePlayerList();

    void shufflePlayer();

    void showGameView();

}