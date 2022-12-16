/*
 * Created by JFormDesigner on Fri Dec 16 13:34:51 CET 2022
 */

package org.jinx.view;

import org.jinx.presenter.interfaces.IPlayerManagerPresenter;
import org.jinx.swing.SwingColors;
import org.jinx.view.interfaces.IPlayerManagerView;

import java.awt.*;
import javax.swing.*;


/**
 * @author unknown
 */
public class PlayerManagerView extends JPanel implements IPlayerManagerView {

    private JLabel playername1;
    private JLabel playername2;
    private JLabel playername3;
    private JLabel playername4;
    private JButton histroyButton;
    private JButton histroyButton2;
    private JButton histroyButton3;
    private JButton histroyButton4;
    private JButton nextButton;
    private JButton shuffleButton;

    public PlayerManagerView() {
        initComponents();
    }

    @Override
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        playername1 = new JLabel();
        playername2 = new JLabel();
        playername3 = new JLabel();
        playername4 = new JLabel();
        histroyButton = new JButton();
        histroyButton2 = new JButton();
        histroyButton3 = new JButton();
        histroyButton4 = new JButton();
        nextButton = new JButton();
        shuffleButton = new JButton();

        //======== this ========
        setBackground(SwingColors.BackGroundColor);
        setPreferredSize(new Dimension(550, 700));
        setMaximumSize(new Dimension(550, 700));
        setMinimumSize(new Dimension(550, 700));
        setLayout(null);

        //---- Playername1 ----
        playername1.setText("Name1");
        playername1.setBackground(SwingColors.BackGroundColor);
        playername1.setForeground(SwingColors.BlueColor);
        playername1.setFont(new Font("Arial", Font.PLAIN, 18));

        add(playername1);
        playername1.setBounds(50, 110, 125, 60);

        //---- Playername2 ----
        playername2.setText("Name2");
        playername2.setFont(new Font("Arial", Font.PLAIN, 18));
        playername2.setBackground(SwingColors.BackGroundColor);
        playername2.setForeground(SwingColors.BlueColor);
        add(playername2);
        playername2.setBounds(50, 270, 125, 55);

        //---- Playername3 ----
        playername3.setText("Name3");
        playername3.setFont(new Font("Arial", Font.PLAIN, 18));
        playername3.setBackground(SwingColors.BackGroundColor);
        playername3.setForeground(SwingColors.BlueColor);
        add(playername3);
        playername3.setBounds(50, 190, 125, 60);

        //---- Playername4 ----
        playername4.setText("Name4");
        playername4.setFont(new Font("Arial", Font.PLAIN, 18));
        playername4.setBackground(SwingColors.BackGroundColor);
        playername4.setForeground(SwingColors.BlueColor);
        add(playername4);
        playername4.setBounds(50, 340, 125, 60);

        //---- histroyButton ----
        histroyButton.setText("Spielhistorie anzeigen ");
        histroyButton.setFont(new Font("Arial", Font.BOLD, 12));
        histroyButton.setBackground(SwingColors.BackGroundColor);
        histroyButton.setForeground(SwingColors.TextColor);
        add(histroyButton);
        histroyButton.setBounds(280, 115, 200, 40);

        //---- histroyButton2 ----
        histroyButton2.setText("Spielhistorie anzeigen ");
        histroyButton2.setFont(new Font("Arial", Font.BOLD, 12));
        histroyButton2.setBackground(SwingColors.BackGroundColor);
        histroyButton2.setForeground(SwingColors.TextColor);
        add(histroyButton2);
        histroyButton2.setBounds(280, 195, 200, 40);

        //---- histroyButton3 ----
        histroyButton3.setText("Spielhistorie anzeigen ");
        histroyButton3.setFont(new Font("Arial", Font.BOLD, 12));
        histroyButton3.setBackground(SwingColors.BackGroundColor);
        histroyButton3.setForeground(SwingColors.TextColor);
        add(histroyButton3);
        histroyButton3.setBounds(280, 270, 200, 40);

        //---- histroyButton4 ----
        histroyButton4.setText("Spielhistorie anzeigen ");
        histroyButton4.setFont(new Font("Arial", Font.BOLD, 12));
        histroyButton4.setBackground(SwingColors.BackGroundColor);
        histroyButton4.setForeground(SwingColors.TextColor);
        add(histroyButton4);
        histroyButton4.setBounds(280, 345, 200, 40);

        //---- nextButton ----
        nextButton.setText("Weiter");
        nextButton.setFont(new Font("Arial", Font.BOLD, 12));
        nextButton.setBackground(SwingColors.BackGroundColor);
        nextButton.setForeground(SwingColors.TextColor);
        add(nextButton);
        nextButton.setBounds(380, 575, nextButton.getPreferredSize().width, 30);
        //nextButton.setBounds(380, 640, 105, 40);

        //---- shuffleButton ----
        shuffleButton.setText("Shuffle Spieler ");
        shuffleButton.setFont(new Font("Arial", Font.BOLD, 12));
        shuffleButton.setBackground(SwingColors.BackGroundColor);
        shuffleButton.setForeground(SwingColors.TextColor);
        add(shuffleButton);
        shuffleButton.setBounds(35, 575, 120,30);
       // shuffleButton.setBounds(35, 640, 105, 40);

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

        //---- bindings ----

        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    @Override
    public void setPresenter(IPlayerManagerPresenter presenter) {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off


    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
