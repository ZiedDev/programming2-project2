package UI.Components;

import UI.Theme.UITheme;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

public class LabeledField<C extends JComponent> extends JPanel {
    private final JLabel label;
    private final C field;

    private LabeledField(String labelText, C field, boolean vertical) {
        this.field = field;
        this.label = new JLabel(labelText != null ? labelText : "");
        this.label.setFont(UITheme.FONT_BODY);
        this.label.setForeground(UITheme.TEXT_LIGHT);

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        if (vertical) {
            setLayout(new BorderLayout(0, 4));
            add(label, BorderLayout.NORTH);
            add(field, BorderLayout.CENTER);
        } else {
            setLayout(new BorderLayout(12, 0));
            add(label, BorderLayout.WEST);
            if (!(field instanceof JTextField)) {
                field.setPreferredSize(new Dimension(UITheme.FORM_CONTROL_WIDTH, field.getPreferredSize().height));
            }
            add(field, BorderLayout.EAST);
        }
    }

    public static <C extends JComponent> LabeledField<C> horizontal(String label, C field) {
        return new LabeledField<>(label, field, false);
    }

    public static <C extends JComponent> LabeledField<C> vertical(String label, C field) {
        return new LabeledField<>(label, field, true);
    }

    public JLabel getLabel() {
        return label;
    }

    public C getField() {
        return field;
    }
}
