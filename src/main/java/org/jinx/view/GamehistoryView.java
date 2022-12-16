/*
 * Created by JFormDesigner on Thu Dec 15 15:11:38 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.interfaces.IGamehistoryPresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IGamehistoryView;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class GamehistoryView extends JPanel implements IGamehistoryView {

    private JLabel playername;
    private JScrollPane scrollPane1;
    private JTextPane gamehistory;
    private JButton nextButton;
    private JButton backButton;
    private JButton backtoscreen;

    private IGamehistoryPresenter presenter;

    public GamehistoryView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        playername = new JLabel();
        scrollPane1 = new JScrollPane();
        gamehistory = new JTextPane();
        nextButton = new JButton();
        backButton = new JButton();
        backtoscreen = new JButton();


        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setPreferredSize(new Dimension(1100, 700));
        setMaximumSize(new Dimension(1100, 700));
        setMinimumSize(new Dimension(1100, 700));
        setLayout(null);

        //---- Playername ----
        playername.setText("Playername");
        playername.setFont(new Font("Arial", Font.PLAIN, 30));
        playername.setBackground(SwingColors.BackGroundColor);
        playername.setForeground(SwingColors.BlueColor);
        add(playername);
        playername.setBounds(60, 60, 275, 70);
        gamehistory.setEditable(false);
        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(gamehistory);
        }
        add(scrollPane1);
        scrollPane1.setBounds(45, 170, 470, 385);

        //---- backtoscreen ----
        backtoscreen.setText("zur\u00fcck");
        backtoscreen.setBackground(SwingColors.BackGroundColor);
        backtoscreen.setForeground(SwingColors.TextColor);
        backtoscreen.addActionListener(e -> presenter.showLoginView());
        add(backtoscreen);
        backtoscreen.setBounds(915, 50, 130, 55);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
    }
    @Override
    public void updatePlayerLabel(String username){
        playername.setText(username);
    }
    @Override
    public void setPresenter(IGamehistoryPresenter presenter) {
        this.presenter = presenter;
    }


}
