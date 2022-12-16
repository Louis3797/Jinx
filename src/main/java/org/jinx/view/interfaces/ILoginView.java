package org.jinx.view.interfaces;

import org.jinx.player.AgentDifficulty;
import org.jinx.presenter.interfaces.ILoginPresenter;

public interface ILoginView extends IView<ILoginPresenter, ILoginView> {

    /**
     * Displays success message on status label if this method is called
     */
    void updateStatusLabelSuccess();

    /**
     * Displays error message on status label if this method is called
     */
    void updateStatusLabelError();

    /**
     * Displays error message on status label if this method is called
     */
    void updateStatusLabelErrorEmptyTextField();

    /**
     * updates the playerManagerView labels and buttons
     * @param difficulty difficulty of bot if player is bot
     */
    void updatePlayerManagerView(AgentDifficulty difficulty);
}
