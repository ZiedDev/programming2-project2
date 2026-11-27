package UI.Components;

import UI.Theme.UITheme;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class InputField extends JTextField {
    private Consumer<String> onChange;
    private DocumentListener changeListener;
    private String lastValue;

    public InputField() {
        this("", UITheme.FORM_CONTROL_COLUMNS);
    }

    public InputField(String initialText) {
        this(initialText, UITheme.FORM_CONTROL_COLUMNS);
    }

    public InputField(String initialText, int columns) {
        super(initialText != null ? initialText : "", columns);
        this.lastValue = getValue();

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

    public InputField value(String text) {
        if (changeListener != null) {
            getDocument().removeDocumentListener(changeListener);
        }
        setText(text != null ? text : "");
        this.lastValue = getValue();
        if (changeListener != null) {
            getDocument().addDocumentListener(changeListener);
        }
        return this;
    }

    public InputField columns(int columns) {
        setColumns(columns);
        return this;
    }

    public InputField onChange(Consumer<String> onChange) {
        this.onChange = onChange;
        if (changeListener == null && onChange != null) {
            changeListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    commit();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    commit();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // Plain text fields do not fire attribute-only changes.
                }
            };
            getDocument().addDocumentListener(changeListener);
        }
        return this;
    }

    public String getValue() {
        return getText().trim();
    }

    public void commit() {
        String current = getValue();
        if (!current.equals(lastValue)) {
            lastValue = current;
            if (onChange != null) {
                onChange.accept(current);
            }
        }
    }
}
