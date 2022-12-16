/*
 * Created by JFormDesigner on Thu Dec 15 14:34:41 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.interfaces.IHighscorePresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IHighscoreView;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class HighscoreView extends JPanel implements IHighscoreView {
    private JLabel title;
    private JScrollPane scrollPane1;
    private JTextPane highscorePane1;
    private JButton backButton;

    private IHighscorePresenter presenter;
    public HighscoreView() {
        initComponents();
    }
    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        title = new JLabel();
        scrollPane1 = new JScrollPane();
        highscorePane1 = new JTextPane();
        backButton = new JButton();

        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setPreferredSize(new Dimension(1100, 700));
        setMinimumSize(new Dimension(1100, 700));
        setMaximumSize(new Dimension(1100, 700));
        setLayout(null);

        //---- title ----
        title.setText("Highscore");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBackground(SwingColors.BackGroundColor);
        title.setForeground(SwingColors.BlueColor);
        add(title);
        title.setBounds(465, 25, 175, 90);
        highscorePane1.setFont(new Font("Arial",Font.PLAIN, 24));
        highscorePane1.setEditable(false);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(highscorePane1);
        }
        add(scrollPane1);
        scrollPane1.setBounds(400, 135, 300, 405);

        //---- button1 ----
        backButton.setText("Zur\u00fcck");
        backButton.setBackground(SwingColors.BackGroundColor);
        backButton.setForeground(SwingColors.TextColor);
        backButton.addActionListener(e -> presenter.showStartView());
        add(backButton);
        backButton.setBounds(490, 600, 115, 40);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
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
    public void updateHighscorelist(String highscore) {
        highscorePane1.setText(highscore);
    }


    @Override
    public void setPresenter(IHighscorePresenter presenter) {
        this.presenter = presenter;
        presenter.readHighscore();
    }
}
