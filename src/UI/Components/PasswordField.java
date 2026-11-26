package UI.Components;

import UI.Theme.UITheme;

import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField {
    public PasswordField() {
        super(UITheme.FORM_CONTROL_COLUMNS);
    }

    public PasswordField(int columns) {
        super(columns);
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
