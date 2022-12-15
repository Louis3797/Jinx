/*
 * Created by JFormDesigner on Thu Dec 15 15:11:38 CET 2022
 */

package org.jinx.view;

import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IGamehistoryView;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class GamehistoryView extends JPanel implements IGamehistoryView {

    private JLabel Playername;
    private JScrollPane scrollPane1;
    private JTextPane gamehistory;
    private JButton nextButton;
    private JButton backButton;
    private JButton backtoscreen;

    public GamehistoryView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        Playername = new JLabel();
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
        Playername.setText("Playername");
        Playername.setFont(new Font("Arial", Font.PLAIN, 30));
        Playername.setBackground(SwingColors.BackGroundColor);
        Playername.setForeground(SwingColors.BlueColor);
        add(Playername);
        Playername.setBounds(60, 60, 275, 70);
        gamehistory.setEditable(false);
        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(gamehistory);
        }
        add(scrollPane1);
        scrollPane1.setBounds(45, 170, 470, 385);

        //---- next ----
        nextButton.setText("n\u00e4chte Historie");
        nextButton.setBackground(SwingColors.BackGroundColor);
        nextButton.setForeground(SwingColors.TextColor);
        add(nextButton);
        nextButton.setBounds(385, 605, 130, 55);

        //---- back ----
        backButton.setText("letzte Historie");
        backButton.setBackground(SwingColors.BackGroundColor);
        backButton.setForeground(SwingColors.TextColor);
        add(backButton);
        backButton.setBounds(45, 605, 130, 55);

        //---- backtoscreen ----
        backtoscreen.setText("zur\u00fcck");
        backtoscreen.setBackground(SwingColors.BackGroundColor);
        backtoscreen.setForeground(SwingColors.TextColor);
        add(backtoscreen);
        backtoscreen.setBounds(915, 50, 130, 55);

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
    public void updateStatusTextAreaError() {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off

    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
