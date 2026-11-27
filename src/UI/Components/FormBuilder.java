package UI.Components;

import javax.swing.JPanel;

public interface FormBuilder<T> {
    JPanel getPanel();

    T build();
}
