package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IPlayerManagerView;

public interface IPlayerManagerPresenter extends IPresenter<IPlayerManagerPresenter, IPlayerManagerView> {


    void shufflePlayer();

    void showGameView();

    void showHistoryView();

}
