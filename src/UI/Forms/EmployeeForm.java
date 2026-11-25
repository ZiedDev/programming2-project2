package UI.Forms;

import Model.Employee;
import UI.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeForm implements FormBuilder<Employee> {
    private final JPanel panel = new JPanel();
    private final JComboBox<FormBuilder<Employee>> typeBox;

    public EmployeeForm(List<FormBuilder<Employee>> typeForms) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.setOpaque(false);
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (FormBuilder<Employee> form : typeForms) {
            cards.add(form.getPanel(), form.toString());
        }

        @SuppressWarnings("unchecked")
        FormBuilder<Employee>[] options = typeForms.toArray(new FormBuilder[0]);
        typeBox = ComponentFactory.createComboBox(options, selected -> {
            if (selected != null) {
                cardLayout.show(cards, selected.toString());
                cards.revalidate();
                cards.repaint();
                Window w = SwingUtilities.getWindowAncestor(panel);
                if (w != null)
                    w.pack();
            }
        });

        panel.add(ComponentFactory.createFormRow("Type", typeBox));
        panel.add(cards);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Employee build() {
        FormBuilder<Employee> selected = (FormBuilder<Employee>) typeBox.getSelectedItem();
        if (selected == null) {
            throw new IllegalArgumentException("Please select an employee type.");
        }
        return selected.build();
    }
}