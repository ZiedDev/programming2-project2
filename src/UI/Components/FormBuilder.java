package UI.Components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

// public interface FormBuilder<T> {
//     JPanel getPanel();

//     T build();
// }

public abstract class FormBuilder<T> extends JPanel {
    protected FormBuilder() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
    }
    
    public abstract T build();
}