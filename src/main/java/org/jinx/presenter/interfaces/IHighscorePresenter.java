package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IHighscoreView;

public interface IHighscorePresenter extends IPresenter<IHighscorePresenter, IHighscoreView> {

    /**
     * shows start view
     */
    void showStartView();

    /**
     * reads highscore to textpane
     */
    void readHighscore();
}
