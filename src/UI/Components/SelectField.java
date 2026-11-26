package UI.Components;

import javax.swing.JComboBox;
import java.util.List;
import java.util.function.Consumer;

public class SelectField<T> extends JComboBox<T> {
    public SelectField() {
    }

    public SelectField(List<T> items) {
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
