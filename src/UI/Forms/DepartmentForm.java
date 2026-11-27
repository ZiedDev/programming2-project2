package UI.Forms;

import UI.Components.*;

public class DepartmentForm extends FormBuilder<String> {
    private final InputField nameField;

    public DepartmentForm(String initialName) {
        nameField = new InputField(initialName);

        add(LabeledField.horizontal("Department Name", nameField));
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