package UI.Pages.Departments;

import UI.Components.*;

import javax.swing.*;

public class DepartmentForm implements FormBuilder<String> {
    private final JPanel panel = new JPanel();
    private final InputFieldNew nameField;

    public DepartmentForm(String initialName) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        nameField = new InputFieldNew(initialName);

        panel.add(LabeledField.horizontal("Department Name", nameField));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public String build() {
        String name = nameField.getValue();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        return name;
    }
}