package UI.Forms;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import UI.ComponentFactory;

import javax.swing.*;
import java.util.List;

public class FullTimeForm implements FormBuilder<Employee> {
    private final JPanel panel = new JPanel();
    private final JTextField nameField = new JTextField(20);
    private final JComboBox<Department> deptBox;
    private final JTextField ageField = new JTextField(20);
    private final JTextField positionField = new JTextField(20);
    private final JTextField salaryField = new JTextField(20);
    private final JTextField bonusField = new JTextField(20);

    public FullTimeForm(List<Department> departments) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        deptBox = new JComboBox<>(departments.toArray(new Department[0]));

        panel.add(ComponentFactory.createFormRow("Full Name", nameField));
        panel.add(ComponentFactory.createFormRow("Department", deptBox));
        panel.add(ComponentFactory.createFormRow("Age", ageField));
        panel.add(ComponentFactory.createFormRow("Position", positionField));
        panel.add(ComponentFactory.createFormRow("Basic Salary", salaryField));
        panel.add(ComponentFactory.createFormRow("Bonus", bonusField));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Employee build() {
        try {
            return new FullTimeEmployee(
                    nameField.getText().trim(),
                    (Department) deptBox.getSelectedItem(),
                    Integer.parseInt(ageField.getText().trim()),
                    positionField.getText().trim(),
                    Double.parseDouble(salaryField.getText().trim()),
                    Double.parseDouble(bonusField.getText().trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age, Basic Salary, and Bonus must be valid numbers.");
        }
    }
}