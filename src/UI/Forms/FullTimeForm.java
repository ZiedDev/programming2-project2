package UI.Forms;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import UI.Components.*;

import java.util.List;

public class FullTimeForm extends FormBuilder<Employee> {
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

        add(LabeledField.horizontal("Full Name", nameField));
        add(LabeledField.horizontal("Department", deptBox));
        add(LabeledField.horizontal("Age", ageField));
        add(LabeledField.horizontal("Position", positionField));
        add(LabeledField.horizontal("Basic Salary", salaryField));
        add(LabeledField.horizontal("Bonus", bonusField));
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