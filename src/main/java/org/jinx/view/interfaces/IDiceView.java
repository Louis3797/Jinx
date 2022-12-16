package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IDicePresenter;

public interface IDiceView extends IView<IDicePresenter, IDiceView> {

    /**
     * updates dice with new result
     * @param dicePosition value of dice
     */
    void updateDice(int dicePosition);

}
