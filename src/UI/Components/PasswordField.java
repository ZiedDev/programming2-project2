package UI.Components;

import UI.Theme.UITheme;

import javax.swing.*;

public class PasswordField extends JPasswordField {
    public PasswordField() {
        this(UITheme.FORM_CONTROL_COLUMNS);
    }

    public PasswordField(int columns) {
        super(columns);

        setBackground(UITheme.BG_DARK);
        setForeground(UITheme.TEXT_LIGHT);
        setCaretColor(UITheme.PRIMARY);
        setSelectionColor(UITheme.PRIMARY);
        setSelectedTextColor(UITheme.TEXT_DARK);
        setFont(UITheme.FONT_BODY);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    }

    public PasswordField columns(int columns) {
        setColumns(columns);
        return this;
    }

    public PasswordField clear() {
        setText("");
        return this;
    }
}
