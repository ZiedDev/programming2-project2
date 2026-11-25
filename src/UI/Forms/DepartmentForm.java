package UI.Forms;

import UI.ComponentFactory;

import javax.swing.*;

public class DepartmentForm implements FormBuilder<String> {
    private final JPanel panel = new JPanel();
    private final JTextField nameField = new JTextField(20);

    public DepartmentForm(String initialName) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        if (initialName != null) {
            nameField.setText(initialName);
        }

        panel.add(ComponentFactory.createFormRow("Department Name", nameField));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public String build() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        return name;
    }
}