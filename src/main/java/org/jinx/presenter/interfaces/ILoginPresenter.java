package org.jinx.presenter.interfaces;

import org.jinx.player.AgentDifficulty;
import org.jinx.view.interfaces.ILoginView;

public interface ILoginPresenter extends IPresenter<ILoginPresenter, ILoginView> {

    void login(String username, String password, AgentDifficulty difficulty);
//    {
//
//        boolean exists = loginManager.checkCred..(username, password);
//
//        view.updateStatus(exists);
//
//        if(difficulty == null){
//        }else {
//
//        }
//
//    }

    void showRegisterView();


}
