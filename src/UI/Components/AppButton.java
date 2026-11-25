package UI.Components;

import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.*;
import java.awt.*;

public class AppButton extends JButton {
    private final IconColor defaultIconColor;

    protected AppButton(String text, Color background, Color foreground, IconColor defaultIconColor) {
        super(text);
        this.defaultIconColor = defaultIconColor;

        setMaximumSize(new Dimension(Integer.MAX_VALUE, UITheme.BUTTON_HEIGHT));
        setSize(new Dimension(getPreferredSize().width, UITheme.BUTTON_HEIGHT));
        setBackground(background);
        setForeground(foreground);
        setFont(UITheme.FONT_BODY);
        setFocusPainted(false);
        setOpaque(true);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static AppButton primary(String text) {
        return new AppButton(text, UITheme.PRIMARY, UITheme.TEXT_DARK, IconColor.DARK);
    }

    public static AppButton secondary(String text) {
        return new AppButton(text, UITheme.BG_DARK, UITheme.TEXT_LIGHT, IconColor.LIGHT);
    }

    public static AppButton danger(String text) {
        return new AppButton(text, UITheme.DANGER, UITheme.TEXT_DARK, IconColor.DARK);
    }

    public AppButton icon(AppIcon icon) {
        return icon(icon, this.defaultIconColor);
    }

    public AppButton icon(AppIcon icon, IconColor color) {
        if (icon != null && color != null) {
            setIcon(icon.get(color, UITheme.ICON_SIZE));
        }
        return this;
    }

    public AppButton alignLeft() {
        setHorizontalAlignment(SwingConstants.LEFT);
        return this;
    }

    public AppButton alignCenter() {
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }

    public AppButton alignX(float alignment) {
        setAlignmentX(alignment);
        return this;
    }

    public AppButton onClick(Runnable action) {
        if (action != null) {
            addActionListener(e -> action.run());
        }
        return this;
    }
}

