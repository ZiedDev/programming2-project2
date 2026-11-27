package UI.Components;

import UI.Theme.UITheme;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class SelectField<T> extends JComboBox<T> {
    public SelectField() {
        setBackground(UITheme.BG_DARK);
        setForeground(UITheme.TEXT_LIGHT);
        setFont(UITheme.FONT_BODY);
        setFocusable(false);
    }

    public SelectField(List<T> items) {
        this();
        options(items);
    }

    public SelectField<T> options(List<T> items) {
        removeAllItems();
        if (items != null) {
            for (T item : items) {
                addItem(item);
            }
        }
        return this;
    }

    public SelectField<T> selected(T item) {
        setSelectedItem(item);
        return this;
    }

    public SelectField<T> onChange(Consumer<T> onChange) {
        if (onChange != null) {
            addActionListener(e -> {
                T selected = getSelected();
                if (selected != null) {
                    onChange.accept(selected);
                }
            });
        }
        return this;
    }

    public T getSelected() {
        int index = getSelectedIndex();
        return index >= 0 ? getItemAt(index) : null;
    }
}
