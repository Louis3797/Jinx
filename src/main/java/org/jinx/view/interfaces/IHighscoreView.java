package org.jinx.view.interfaces;

import org.jinx.highscore.HighScore;
import org.jinx.highscore.HighScoreList;
import org.jinx.presenter.interfaces.IHighscorePresenter;

import java.util.List;

public interface IHighscoreView extends IView<IHighscorePresenter,IHighscoreView> {

    /**
     * Inits view
     */
    void initComponents();


    /**
     * Method to update Highscorelist
     * @param highscore
     */
    void updateHighscorelist(String highscore);


}
