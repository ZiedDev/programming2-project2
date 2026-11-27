package UI.Forms;

import Model.Employee;
import UI.Components.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeForm extends FormBuilder<Employee> {
    private final SelectField<FormBuilder<Employee>> typeBox;

    public EmployeeForm(List<FormBuilder<Employee>> typeForms) {
        this(typeForms, null);
    }

    public EmployeeForm(List<FormBuilder<Employee>> typeForms, Employee initialEmployee) {
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.setOpaque(false);
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (FormBuilder<Employee> form : typeForms) {
            cards.add(form, form.toString());
        }

        typeBox = new SelectField<>(typeForms).onChange(selected -> {
            cardLayout.show(cards, selected.toString());
            cards.revalidate();
            cards.repaint();
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null)
                w.pack();
        });

        if (initialEmployee != null) {
            for (FormBuilder<Employee> form : typeForms) {
                if (form.toString().equals(initialEmployee.getEmployeeType())) {
                    typeBox.selected(form);
                    cardLayout.show(cards, form.toString());
                    break;
                }
            }
            typeBox.setEnabled(false);
        }

        add(LabeledField.horizontal("Type", typeBox));
        add(cards);
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