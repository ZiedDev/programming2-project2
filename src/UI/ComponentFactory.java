package UI;

import UI.UITheme.AppIcon;
import UI.UITheme.IconColor;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;
import java.util.function.Consumer;
import java.awt.event.ActionListener;

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
        if (icon != null && color != null)
            btn.setIcon(icon.get(color, ICON_SIZE));
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

    public static JButton createSidebarButton(String text, AppIcon icon, IconColor activeColor,
            IconColor inactiveColor) {
        JButton btn = createButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        if (icon != null) {
            if (activeColor != null)
                btn.putClientProperty(ICON_ACTIVE, icon.get(activeColor, ICON_SIZE));
            if (inactiveColor != null)
                btn.putClientProperty(ICON_INACTIVE, icon.get(inactiveColor, ICON_SIZE));
        }
        setSidebarButtonActive(btn, false);
        return btn;
    }

    // Others
    public static void setSidebarButtonActive(JButton btn, boolean isActive) {
        if (btn == null)
            return;
        btn.setBackground(isActive ? UITheme.PRIMARY : UITheme.BG_DARK);
        btn.setForeground(isActive ? UITheme.TEXT_DARK : UITheme.TEXT_LIGHT);
        btn.setFont(isActive ? UITheme.FONT_SUBTITLE : UITheme.FONT_BODY);

        ImageIcon icon = (ImageIcon) btn.getClientProperty(isActive ? ICON_ACTIVE : ICON_INACTIVE);
        if (icon != null) {
            btn.setIcon(icon);
        }
    }

    // States Cards
    private static final String STAT_VALUE = "stat.value";

    public static JPanel createStatCard(String title, String value) {
        return createStatCard(title, value, null, null);
    }

    public static JPanel createStatCard(String title, int value) {
        return createStatCard(title, String.valueOf(value), null, null);
    }

    public static JPanel createStatCard(String title, String value, AppIcon icon) {
        return createStatCard(title, value, icon, IconColor.DARK);
    }

    public static JPanel createStatCard(String title, int value, AppIcon icon) {
        return createStatCard(title, String.valueOf(value), icon, IconColor.DARK);
    }

    public static JPanel createStatCard(String title, String value, AppIcon icon, IconColor color) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(UITheme.SURFACE_BASE);
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JPanel CardHeader = new JPanel();
        CardHeader.setLayout(new BoxLayout(CardHeader, BoxLayout.Y_AXIS));
        CardHeader.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(UITheme.FONT_SUBTITLE.deriveFont(Font.BOLD));
        titleLabel.setForeground(UITheme.TEXT_DARK);
        if (icon != null && color != null) {
            titleLabel.setIcon(icon.get(color, ICON_SIZE));
            titleLabel.setIconTextGap(8);
        }
        CardHeader.add(titleLabel);

        JPanel separator = createHorizontalSeparator();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        CardHeader.add(separator);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(UITheme.FONT_DISPLAY.deriveFont(Font.BOLD));
        valueLabel.setForeground(UITheme.TEXT_DARK);

        card.putClientProperty(STAT_VALUE, valueLabel);
        card.add(CardHeader, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    public static void setStatCardValue(JPanel card, String value) {
        if (card == null)
            return;
        JLabel valueLabel = (JLabel) card.getClientProperty(STAT_VALUE);
        if (valueLabel != null) {
            valueLabel.setText(value);
        }
    }

    public static void setStatCardValue(JPanel card, int value) {
        setStatCardValue(card, String.valueOf(value));
    }

    // Separator
    public static JPanel createHorizontalSeparator() {
        return createHorizontalSeparator(UITheme.BORDER);
    }

    public static JPanel createHorizontalSeparator(Color color) {
        JPanel separator = new JPanel();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        separator.setPreferredSize(new Dimension(0, 22));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        separator.setOpaque(false);
        separator.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                BorderFactory.createMatteBorder(0, 0, 1, 0, color)));
        return separator;
    }

    public static JPanel createVerticalSeparator() {
        return createVerticalSeparator(UITheme.BORDER);
    }

    public static JPanel createVerticalSeparator(Color color) {
        JPanel separator = new JPanel();
        separator.setMaximumSize(new Dimension(12, Integer.MAX_VALUE));
        separator.setPreferredSize(new Dimension(12, 0));
        separator.setAlignmentY(Component.CENTER_ALIGNMENT);
        separator.setOpaque(false);
        separator.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 0),
                BorderFactory.createMatteBorder(0, 0, 0, 1, color)));
        return separator;
    }

    public static InputField createInputField(String initialText, int columns, Consumer<String> onCommit) {
        InputField field = new InputField(initialText, columns, onCommit);
        // field.setBackground(UITheme.BG_BASE);
        return field; // will perhaps need more stuff ?
    }

    public static JPanel createInputField(String desc, String initialText, int columns, Consumer<String> onCommit) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(desc);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(UITheme.FONT_BODY);
        label.setForeground(UITheme.TEXT_LIGHT);

        InputField inputField = createInputField(initialText, columns, onCommit);
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label, BorderLayout.WEST);
        panel.add(inputField, BorderLayout.EAST);
        return panel;
    }

    public static JComboBox<String> createComboBox(Object[] options, Consumer<String> onChange) {
        JComboBox<String> comboBox = new JComboBox<>();
        for (Object x : options) {
            comboBox.addItem(x.toString());
        }

        comboBox.addActionListener(e -> {
            String selected = (String) comboBox.getSelectedItem();
            onChange.accept(selected);
        });

        return comboBox;
    }

    public static Popup createPopup(String title, JFrame parent) {
        Popup x = new Popup(title, parent);
        return x;
    }

    public static JPanel createMultipleInputsPanel(String[] fields) { // do i really need to wrap it in a jpanel though
                                                                      // ?
        JPanel panel = new JPanel();
        panel.setBackground(UITheme.BG_DARK);
        MultipleInputField field = new MultipleInputField(fields);
        field.setBackground(UITheme.BG_DARK);
        panel.add(field);
        return panel;
    }

    public static Popup createInputsPopup(String[] fields, String title, JFrame parent) {
        Popup x = createPopup(title, parent);
        x.addComponent(createMultipleInputsPanel(fields));
        return x;
    }
}