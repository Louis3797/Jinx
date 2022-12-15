package org.jinx.view.interfaces;

import org.jinx.presenter.interfaces.IDicePresenter;

public interface IDiceView extends IView<IDicePresenter, IDiceView> {

    void updateDice(int dicePosition);

}
