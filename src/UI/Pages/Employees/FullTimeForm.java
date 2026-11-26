package UI.Pages.Employees;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import UI.Components.*;

import javax.swing.*;
import java.util.List;

public class FullTimeForm implements FormBuilder<Employee> {
    private final JPanel panel = new JPanel();
    private final SelectField<Department> deptBox;
    private final InputFieldNew nameField = new InputFieldNew("");
    private final InputFieldNew ageField = new InputFieldNew("");
    private final InputFieldNew positionField = new InputFieldNew("");
    private final InputFieldNew salaryField = new InputFieldNew("");
    private final InputFieldNew bonusField = new InputFieldNew("");

    public FullTimeForm(List<Department> departments) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        deptBox = new SelectField<Department>(departments);

        panel.add(LabeledField.horizontal("Full name", nameField));
        panel.add(LabeledField.horizontal("Department", deptBox));
        panel.add(LabeledField.horizontal("Age", ageField));
        panel.add(LabeledField.horizontal("Position", positionField));
        panel.add(LabeledField.horizontal("Basic Salary", salaryField));
        panel.add(LabeledField.horizontal("Bonus", bonusField));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Employee build() {
        try {
            return new FullTimeEmployee(
                    nameField.getValue(),
                    deptBox.getSelected(),
                    Integer.parseInt(ageField.getValue()),
                    positionField.getValue(),
                    Double.parseDouble(salaryField.getValue()),
                    Double.parseDouble(bonusField.getValue()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age, Basic Salary, and Bonus must be valid numbers.");
        }
    }

    @Override
    public String toString() {
        return "Full-Time";
    }
}