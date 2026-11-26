package UI.Components;

import UI.Theme.UITheme;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.function.Consumer;

public class InputFieldNew extends JTextField {
    private Consumer<String> onChange;
    private DocumentListener changeListener;
    private String lastValue;

    public InputFieldNew() {
        this("", UITheme.FORM_CONTROL_COLUMNS);
    }

    public InputFieldNew(String initialText) {
        this(initialText, UITheme.FORM_CONTROL_COLUMNS);
    }

    public InputFieldNew(String initialText, int columns) {
        super(initialText != null ? initialText : "", columns);
        this.lastValue = getValue();
    }

    public InputFieldNew value(String text) {
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

    public InputFieldNew columns(int columns) {
        setColumns(columns);
        return this;
    }

    public InputFieldNew onChange(Consumer<String> onChange) {
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
