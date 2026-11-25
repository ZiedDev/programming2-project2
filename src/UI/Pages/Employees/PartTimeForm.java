package UI.Pages.Employees;

import Model.Department;
import Model.Employee;
import Model.PartTimeEmployee;
import UI.Components.ComponentFactory;
import UI.Components.FormBuilder;

import javax.swing.*;
import java.util.List;

public class PartTimeForm implements FormBuilder<Employee> {
    private final JPanel panel = new JPanel();
    private final JTextField nameField = new JTextField(20);
    private final JComboBox<Department> deptBox;
    private final JTextField ageField = new JTextField(20);
    private final JTextField positionField = new JTextField(20);
    private final JTextField rateField = new JTextField(20);
    private final JTextField hoursField = new JTextField(20);

    public PartTimeForm(List<Department> departments) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        deptBox = new JComboBox<>(departments.toArray(new Department[0]));

        panel.add(ComponentFactory.createFormRow("Full Name", nameField));
        panel.add(ComponentFactory.createFormRow("Department", deptBox));
        panel.add(ComponentFactory.createFormRow("Age", ageField));
        panel.add(ComponentFactory.createFormRow("Position", positionField));
        panel.add(ComponentFactory.createFormRow("Hourly Rate", rateField));
        panel.add(ComponentFactory.createFormRow("Hours / Month", hoursField));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Employee build() {
        try {
            return new PartTimeEmployee(
                    nameField.getText().trim(),
                    (Department) deptBox.getSelectedItem(),
                    Integer.parseInt(ageField.getText().trim()),
                    positionField.getText().trim(),
                    Double.parseDouble(rateField.getText().trim()),
                    Integer.parseInt(hoursField.getText().trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age, Hourly Rate, and Hours / Month must be valid numbers.");
        }
    }

    @Override
    public String toString() {
        return "Part-Time";
    }
}