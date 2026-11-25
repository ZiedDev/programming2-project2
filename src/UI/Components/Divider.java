package UI.Components;

import UI.Theme.UITheme;

import javax.swing.*;
import java.awt.*;

public class Divider extends JPanel {
    private Divider() {
        setOpaque(false);
    }

    public static Divider horizontal() {
        return horizontal(UITheme.BORDER);
    }

    public static Divider horizontal(Color color) {
        Divider divider = new Divider();
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        divider.setPreferredSize(new Dimension(0, 22));
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);
        divider.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), BorderFactory.createMatteBorder(0, 0, 1, 0, color)));
        return divider;
    }

    public static Divider vertical() {
        return vertical(UITheme.BORDER);
    }

    public static Divider vertical(Color color) {
        Divider divider = new Divider();
        divider.setMaximumSize(new Dimension(12, Integer.MAX_VALUE));
        divider.setPreferredSize(new Dimension(12, 0));
        divider.setAlignmentY(Component.CENTER_ALIGNMENT);
        divider.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0), BorderFactory.createMatteBorder(0, 0, 0, 1, color)));
        return divider;
    }
}
