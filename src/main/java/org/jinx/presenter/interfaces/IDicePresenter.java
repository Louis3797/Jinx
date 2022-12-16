package org.jinx.presenter.interfaces;

import org.jinx.view.interfaces.IDiceView;

public interface IDicePresenter extends IPresenter<IDicePresenter, IDiceView> {

   /**
    * uses dice
    */
   void useDice();

   /**
    * update dice view
    */
   void updateView();
}
