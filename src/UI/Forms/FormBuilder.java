package UI.Forms;

import javax.swing.JPanel;

public interface FormBuilder<T> {
    JPanel getPanel();

    T build();
}