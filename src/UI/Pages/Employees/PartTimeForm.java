package UI.Pages.Employees;

import Model.Department;
import Model.Employee;
import Model.PartTimeEmployee;
import UI.Components.*;

import javax.swing.*;
import java.util.List;

public class PartTimeForm implements FormBuilder<Employee> {
    private final JPanel panel = new JPanel();
    private final SelectField<Department> deptBox;
    private final InputFieldNew nameField = new InputFieldNew("");
    private final InputFieldNew ageField = new InputFieldNew("");
    private final InputFieldNew positionField = new InputFieldNew("");
    private final InputFieldNew rateField = new InputFieldNew("");
    private final InputFieldNew hoursField = new InputFieldNew("");

    public PartTimeForm(List<Department> departments) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        deptBox = new SelectField<>(departments);

        panel.add(LabeledField.horizontal("Full Name", nameField));
        panel.add(LabeledField.horizontal("Department", deptBox));
        panel.add(LabeledField.horizontal("Age", ageField));
        panel.add(LabeledField.horizontal("Position", positionField));
        panel.add(LabeledField.horizontal("Hourly Rate", rateField));
        panel.add(LabeledField.horizontal("Hours / Month", hoursField));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Employee build() {
        try {
            return new PartTimeEmployee(
                    nameField.getValue(),
                    deptBox.getSelected(),
                    Integer.parseInt(ageField.getValue()),
                    positionField.getValue(),
                    Double.parseDouble(rateField.getValue()),
                    Integer.parseInt(hoursField.getValue()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age, Hourly Rate, and Hours / Month must be valid numbers.");
        }
    }

    @Override
    public String toString() {
        return "Part-Time";
    }
}