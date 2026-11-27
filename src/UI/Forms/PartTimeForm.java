package UI.Forms;

import Model.Department;
import Model.Employee;
import Model.PartTimeEmployee;
import UI.Components.*;

import java.util.List;

public class PartTimeForm extends FormBuilder<Employee> {
    private final SelectField<Department> deptBox;
    private final InputField nameField = new InputField("");
    private final InputField ageField = new InputField("");
    private final InputField positionField = new InputField("");
    private final InputField rateField = new InputField("");
    private final InputField hoursField = new InputField("");

    public PartTimeForm(List<Department> departments) {
        this(departments, null);
    }

    public PartTimeForm(List<Department> departments, Employee initialEmployee) {
        deptBox = new SelectField<>(departments);

        if (initialEmployee != null) {
            nameField.value(initialEmployee.getFullName());
            deptBox.selected(initialEmployee.getDepartment());
            ageField.value(String.valueOf(initialEmployee.getAge()));
            positionField.value(initialEmployee.getPosition());
            if (initialEmployee instanceof PartTimeEmployee pt) {
                rateField.value(String.valueOf(pt.getHourlyRate()));
                hoursField.value(String.valueOf(pt.getHoursPerMonth()));
            }
        }

        add(LabeledField.horizontal("Full Name", nameField));
        add(LabeledField.horizontal("Age", ageField));
        add(LabeledField.horizontal("Department", deptBox));
        add(LabeledField.horizontal("Position", positionField));
        add(LabeledField.horizontal("Hourly Rate", rateField));
        add(LabeledField.horizontal("Hours / Month", hoursField));
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