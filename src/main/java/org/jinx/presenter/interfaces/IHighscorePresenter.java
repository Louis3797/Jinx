package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IHighscoreView;

import javax.swing.*;

public interface IHighscorePresenter extends IPresenter<IHighscorePresenter, IHighscoreView> {

    void showStartView();

    void readHighscore();
}
