package UI;

import javax.swing.*;
import java.awt.*;

public class ComponentFactory {
    private ComponentFactory() {}

    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setForeground(UITheme.TEXT_DARK);
        btn.setFocusPainted(true);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        JButton btn = createButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        setSidebarButtonActive(btn, false);
        return btn;
    }

    public static void setSidebarButtonActive(JButton btn, boolean isActive) {
        if (btn == null) return;
        if (isActive) {
            btn.setBackground(UITheme.PRIMARY);
            btn.setForeground(UITheme.TEXT_DARK);
            btn.setFont(UITheme.FONT_SUBTITLE);
        } else {
            btn.setBackground(UITheme.BG_DARK);
            btn.setForeground(UITheme.TEXT_LIGHT);
            btn.setFont(UITheme.FONT_BODY);
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