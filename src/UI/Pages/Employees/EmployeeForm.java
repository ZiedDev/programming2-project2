package UI.Pages.Employees;

import Model.Employee;
import UI.Components.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeForm implements FormBuilder<Employee> {
    private final JPanel panel = new JPanel();
    private final SelectField<FormBuilder<Employee>> typeBox;

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

        typeBox = new SelectField<>(typeForms).onChange(selected -> {
            cardLayout.show(cards, selected.toString());
            cards.revalidate();
            cards.repaint();
            Window w = SwingUtilities.getWindowAncestor(panel);
            if (w != null)
                w.pack();
        });

        panel.add(LabeledField.horizontal("Type", typeBox));
        panel.add(cards);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Employee build() {
        FormBuilder<Employee> selected = typeBox.getSelected();
        if (selected == null) {
            throw new IllegalArgumentException("Please select an employee type.");
        }
        return selected.build();
    }
}