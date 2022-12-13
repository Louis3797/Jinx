package org.jinx.view;

import org.jinx.presenter.ILoginPresenter;
import org.jinx.presenter.IPresenter;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel implements ILoginView {

    IPresenter<ILoginPresenter, ILoginView> presenter;

    public LoginView() {
        createView();
    }


    @Override
    public void createView() {

        setSize(MainFrame.dimension.width / 2, MainFrame.dimension.height);
        setBackground(Color.ORANGE);
        setVisible(true);
    }

    @Override
    public void setPresenter(IPresenter<ILoginPresenter, ILoginView> presenter) {
        this.presenter = presenter;
    }
}
