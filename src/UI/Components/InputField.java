package UI.Components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.function.Consumer;

public class InputField extends JTextField {
    private String lastValue;
    private final Consumer<String> onCommit;

    public InputField(String initialText, int columns, Consumer<String> onCommit) {
        super(initialText, columns);
        this.lastValue = initialText;
        this.onCommit = onCommit;

        /*
         * this.addActionListener(e->commit());
         * 
         * this.addFocusListener(new FocusAdapter() {
         * 
         * @Override
         * public void focusLost(FocusEvent e){
         * commit();
         * }
         * }); will prolly remove this to avoid complication
         */
        this.getDocument().addDocumentListener(new DocumentListener() {
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
                commit();
            }
        });

    }

    public void commit() {
        String current = getText().trim();
        if (!current.equals(lastValue)) {
            lastValue = current;
            onCommit.accept(current);
        }
    }

    public String getValue() {
        return lastValue;
    }

}
