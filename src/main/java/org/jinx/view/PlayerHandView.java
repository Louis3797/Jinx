/*
 * Created by JFormDesigner on Thu Dec 15 16:25:07 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.interfaces.IPlayerHandPresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IPlayerHandView;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class PlayerHandView extends JPanel implements IPlayerHandView {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel usernameLabel;
    private JButton switchHandsButton;

    private JPanel cardsPanel;
    private JScrollPane scrollPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private IPlayerHandPresenter presenter;

    public PlayerHandView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        usernameLabel = new JLabel();
        switchHandsButton = new JButton();
        cardsPanel = new JPanel();
        scrollPane = new JScrollPane(cardsPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //======== this ========
        setMaximumSize(new Dimension(600, 170));
        setMinimumSize(new Dimension(600, 170));
        setPreferredSize(new Dimension(600, 170));
        setBackground(Color.pink);
        setLayout(null);

        //---- usernameLabel ----
        usernameLabel.setText("Playername");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setForeground(Color.black);
        add(usernameLabel);
        usernameLabel.setBounds(205, 15, 200, 35);

        //---- switchHandsButton ----
        switchHandsButton.setText("LuckyCards");
        switchHandsButton.setBackground(SwingColors.BackGroundColor);
        switchHandsButton.setForeground(SwingColors.TextColor);
        add(switchHandsButton);
        switchHandsButton.setBounds(410, 15, 145, 30);

        //---- cardsPanel ----


        //---- scrollpane ----
        add(scrollPane);
        scrollPane.setBounds(5, 55, 560, 115);

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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    @Override
    public void setPresenter(IPlayerHandPresenter presenter) {
        this.presenter = presenter;
    }

}
