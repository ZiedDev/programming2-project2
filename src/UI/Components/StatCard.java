package UI.Components;

import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.*;
import java.awt.*;

public class StatCard extends JPanel{
    private final JLabel titleLabel;
    private final JLabel valueLabel;

    public StatCard(String title, String value) {
        super(new BorderLayout(0, 10));
        setBackground(UITheme.SURFACE_BASE);
        setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(UITheme.FONT_SUBTITLE.deriveFont(Font.BOLD));
        titleLabel.setForeground(UITheme.TEXT_DARK);
        header.add(titleLabel);

        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(separator);

        valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(UITheme.FONT_DISPLAY.deriveFont(Font.BOLD));
        valueLabel.setForeground(UITheme.TEXT_DARK);

        add(header, BorderLayout.NORTH);
        add(valueLabel, BorderLayout.CENTER);
    }

    public StatCard(String title, int value) {
        this(title, String.valueOf(value));
    }

    public StatCard icon(AppIcon icon) {
        return icon(icon, IconColor.DARK);
    }

    public StatCard icon(AppIcon icon, IconColor color) {
        if (icon != null && color != null) {
            titleLabel.setIcon(icon.get(color, UITheme.ICON_SIZE));
            titleLabel.setIconTextGap(8);
        }
        return this;
    }

    public StatCard accentBackground(Color color) {
        setBackground(color);
        return this;
    }

    public void setValue(String value) {
        valueLabel.setText(value);
    }

    public void setValue(int value) {
        setValue(String.valueOf(value));
    }
}
