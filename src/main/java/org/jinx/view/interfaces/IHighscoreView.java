package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IHighscorePresenter;

public interface IHighscoreView extends IView<IHighscorePresenter,IHighscoreView> {

    /**
     * Inits view
     */
    void initComponents();

    /**
     * Displays error message on highscore text area if this method is called
     */
    void updateStatusTextAreaError();


}
