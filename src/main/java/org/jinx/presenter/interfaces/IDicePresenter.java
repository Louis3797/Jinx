package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IDiceView;

public interface IDicePresenter extends IPresenter<IDicePresenter, IDiceView> {

   void useDice();

   void updateView();
}
