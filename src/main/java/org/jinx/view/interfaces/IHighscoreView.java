package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IHighscorePresenter;

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
