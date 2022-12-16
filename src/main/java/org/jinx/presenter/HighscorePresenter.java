package org.jinx.presenter;

import org.jinx.highscore.HighScore;
import org.jinx.highscore.HighScoreList;
import org.jinx.model.IModel;
import org.jinx.presenter.interfaces.IHighscorePresenter;
import org.jinx.view.MainView;
import org.jinx.view.Views;
import org.jinx.view.interfaces.IHighscoreView;



public class HighscorePresenter implements IHighscorePresenter {
    private IHighscoreView view;
    private IModel model;

    public HighscorePresenter(IHighscoreView view, IModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    public void readHighscore(){
        ((HighScoreList) model).getOldHighScores();
        StringBuilder sb = new StringBuilder();
        for (HighScore highScore:  ((HighScoreList) model).getHighScoreList() ) {
            sb.append(highScore.playerName()).append("\t\t\t").append(highScore.highscore()).append("\n");
        }
        view.updateHighscorelist(sb.toString());
    }

    @Override
    public void showStartView() {
        MainView.cardLayout.show(MainView.mainPanel, Views.Start.name());
    }

    @Override
    public IHighscoreView getView() {
        return this.view;
    }

    @Override
    public void setView(IHighscoreView view) {
        this.view = view;
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

}
