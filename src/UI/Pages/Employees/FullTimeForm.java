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
    private final InputField nameField = new InputField("");
    private final InputField ageField = new InputField("");
    private final InputField positionField = new InputField("");
    private final InputField salaryField = new InputField("");
    private final InputField bonusField = new InputField("");

    public FullTimeForm(List<Department> departments) {
        this(departments, null);
    }

    public FullTimeForm(List<Department> departments, Employee initialEmployee) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        deptBox = new SelectField<Department>(departments);

        if (initialEmployee != null) {
            nameField.value(initialEmployee.getFullName());
            deptBox.selected(initialEmployee.getDepartment());
            ageField.value(String.valueOf(initialEmployee.getAge()));
            positionField.value(initialEmployee.getPosition());
            if (initialEmployee instanceof FullTimeEmployee ft) {
                salaryField.value(String.valueOf(ft.getBasicSalary()));
                bonusField.value(String.valueOf(ft.getBonus()));
            }
        }

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