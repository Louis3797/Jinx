/*
 * Created by JFormDesigner on Wed Dec 14 21:51:12 CET 2022
 */

package org.jinx.swing;

import org.jinx.card.CardColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

/**
 * @author unknown
 */
public class NumberCardView extends JPanel {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel numberLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private String number;
    private Color cardColor;

    public NumberCardView(String number, CardColor color) {
        this.number = number;

        // this are all card colors that our game supports
        // RED, BLUE, GREEN, WHITE, CYAN, PINK, YELLOW, BLACK
        // Color class from awt support all of them too.

        try {
            // use reflection to get the color
            Field f = Color.class.getField(color.name());
            this.cardColor = (Color) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }


        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        numberLabel = new JLabel();

        //======== this ========
        setMaximumSize(new Dimension(60, 85));
        setMinimumSize(new Dimension(60, 85));
        setPreferredSize(new Dimension(60, 85));
        setLayout(null);
        //---- numberLabel ----
        numberLabel.setText(number);
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setBackground(cardColor);
        numberLabel.setForeground((cardColor.equals(Color.BLACK) | cardColor.equals(Color.BLUE) )? Color.WHITE : Color.black);
        numberLabel.setOpaque(true);
        add(numberLabel);
        numberLabel.setBounds(0, 0, 60, 85);

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
}
