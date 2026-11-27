package UI.Components;

import UI.PopupOld;
import UI.Theme.UITheme;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static UI.Theme.UITheme.ICON_SIZE;

public class ComponentFactory {
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

    public static <T> JComboBox<T> createComboBox(T[] options, Consumer<T> onChange) {
        JComboBox<T> comboBox = new JComboBox<>(options);
        comboBox.addActionListener(e -> {
            if (onChange != null) {
                onChange.accept(comboBox.getItemAt(comboBox.getSelectedIndex()));
            }
        });
        return comboBox;
    }

    public static <T> JPanel createComboBoxPanelDesc(String desc, T[] options, Consumer<T> onChange) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(desc);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(UITheme.FONT_BODY);
        label.setForeground(UITheme.TEXT_LIGHT);

        int inputWidth = new JTextField(20).getPreferredSize().width;
        JComboBox<T> box = createComboBox(options, onChange);
        box.setPreferredSize(new Dimension(inputWidth, box.getPreferredSize().height));

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label, BorderLayout.WEST);
        panel.add(box, BorderLayout.EAST);

        return panel;
    }
    
    public static JPanel createFormRow(String labelText, JComponent control) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(UITheme.BG_DARK);
        row.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.FONT_BODY);
        label.setForeground(UITheme.TEXT_LIGHT);

        int controlWidth = new JTextField(20).getPreferredSize().width;
        control.setPreferredSize(new Dimension(controlWidth, control.getPreferredSize().height));

        row.add(label, BorderLayout.WEST);
        row.add(control, BorderLayout.EAST);
        return row;
    }
    
    public static UI.PopupOld createPopup(String title, JFrame parent) {
        UI.PopupOld x = new PopupOld(title, parent);
        return x;
    }
}