package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IHighscoreView;

public interface IHighscorePresenter extends IPresenter<IHighscorePresenter, IHighscoreView> {

    void showStartView();

    void readHighscore();
}
