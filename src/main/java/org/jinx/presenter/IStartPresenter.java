package org.jinx.presenter;

import org.jinx.view.IStartView;

public interface IStartPresenter extends IPresenter<IStartPresenter, IStartView> {

    /**
     * Directs Player to Login Screen to add players and start the game from there
     */
    void startGame();

    /**
     * Directs player to Highscore View
     */
    void showHighScoreView();

    /**
     * Sets TextFile as data storage
     */
    void setTextFileAsDataStorage();

    /**
     * Sets Database as data storage
     */
    void setDBAsDataStorage();
}
