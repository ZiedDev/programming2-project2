package UI;

import UI.UITheme.AppIcon;
import UI.UITheme.IconColor;

import javax.swing.*;
import java.awt.*;

public class ComponentFactory {
    private static final int ICON_SIZE = 24;
    private static final int BUTTON_HEIGHT = 64;
    private static final String ICON_ACTIVE = "icon.active";
    private static final String ICON_INACTIVE = "icon.inactive";

    private ComponentFactory() {
    }

    // Without Icon
    private static JButton createButton(String text) {
        JButton btn = new JButton(text);

        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        int currentWidth = btn.getPreferredSize().width;
        btn.setSize(new Dimension(currentWidth, BUTTON_HEIGHT));

        btn.setForeground(UITheme.TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton createPrimaryButton(String text) {
        JButton btn = createButton(text);
        btn.setBackground(UITheme.PRIMARY);
        btn.setFont(UITheme.FONT_BODY);
        return btn;
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = createButton(text);
        btn.setBackground(UITheme.BG_DARK);
        btn.setForeground(UITheme.TEXT_LIGHT);
        btn.setFont(UITheme.FONT_BODY);
        return btn;
    }

    public static JButton createDangerButton(String text) {
        JButton btn = createButton(text);
        btn.setBackground(UITheme.DANGER);
        btn.setFont(UITheme.FONT_BODY);
        return btn;
    }

    public static JButton createSidebarButton(String text) {
        return createSidebarButton(text, null, null, null);
    }

    private static JButton withIcon(JButton btn, AppIcon icon, IconColor color) {
        if (icon != null && color != null) btn.setIcon(icon.get(color, ICON_SIZE));
        return btn;
    }

    // With Icon
    public static JButton createPrimaryButton(String text, AppIcon icon) {
        return createPrimaryButton(text, icon, IconColor.DARK);
    }

    public static JButton createPrimaryButton(String text, AppIcon icon, IconColor color) {
        return withIcon(createPrimaryButton(text), icon, color);
    }

    public static JButton createSecondaryButton(String text, AppIcon icon) {
        return createSecondaryButton(text, icon, IconColor.LIGHT);
    }

    public static JButton createSecondaryButton(String text, AppIcon icon, IconColor color) {
        return withIcon(createSecondaryButton(text), icon, color);
    }

    public static JButton createDangerButton(String text, AppIcon icon) {
        return createDangerButton(text, icon, IconColor.DARK);
    }

    public static JButton createDangerButton(String text, AppIcon icon, IconColor color) {
        return withIcon(createDangerButton(text), icon, color);
    }

    public static JButton createSidebarButton(String text, AppIcon icon) {
        return createSidebarButton(text, icon, IconColor.ACCENT, IconColor.LIGHT);
    }

    public static JButton createSidebarButton(String text, AppIcon icon, IconColor activeColor, IconColor inactiveColor) {
        JButton btn = createButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        if (icon != null) {
            if (activeColor != null)   btn.putClientProperty(ICON_ACTIVE, icon.get(activeColor, ICON_SIZE));
            if (inactiveColor != null) btn.putClientProperty(ICON_INACTIVE, icon.get(inactiveColor, ICON_SIZE));
        }
        setSidebarButtonActive(btn, false);
        return btn;
    }

    // Others
    public static void setSidebarButtonActive(JButton btn, boolean isActive) {
        if (btn == null) return;
        btn.setBackground(isActive ? UITheme.PRIMARY : UITheme.BG_DARK);
        btn.setForeground(isActive ? UITheme.TEXT_DARK : UITheme.TEXT_LIGHT);
        btn.setFont(isActive ? UITheme.FONT_SUBTITLE : UITheme.FONT_BODY);

        ImageIcon icon = (ImageIcon) btn.getClientProperty(isActive ? ICON_ACTIVE : ICON_INACTIVE);
        if (icon != null) {
            btn.setIcon(icon);
        }
    }

    public static JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(UITheme.SURFACE_BASE);
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_BODY);
        titleLabel.setForeground(UITheme.TEXT_DARK);

        valueLabel.setFont(UITheme.FONT_DISPLAY);
        valueLabel.setForeground(UITheme.TEXT_DARK);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    public static JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.FONT_BODY);
        label.setForeground(UITheme.TEXT_LIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 8, 0));
        return label;
    }
}