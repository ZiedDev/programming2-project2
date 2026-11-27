package UI.Forms;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import UI.Components.*;
import UI.Theme.UITheme;

import javax.swing.*;
import java.awt.*;
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
        deptBox = new SelectField<>(departments);

        if (initialEmployee != null) {
            fillFields(initialEmployee);
        }
        add(LabeledField.horizontal("Full Name", nameField));
        add(LabeledField.horizontal("Department", deptBox));
        add(LabeledField.horizontal("Age", ageField));
        add(LabeledField.horizontal("Position", positionField));

        Divider divider = Divider.horizontal();
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(divider);

        // Payment section
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(UITheme.PRIMARY);
        section.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(payRow("Basic Salary", salaryField, "EGP"));
        section.add(payRow("Bonus", bonusField, "EGP"));

        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, section.getPreferredSize().height));
        
        add(section);
    }

    // Form helpers
    private void fillFields(Employee employee) {
        nameField.value(employee.getFullName());
        deptBox.selected(employee.getDepartment());
        ageField.value(String.valueOf(employee.getAge()));
        positionField.value(employee.getPosition());
        if (employee instanceof FullTimeEmployee ft) {
            salaryField.value(String.valueOf(ft.getBasicSalary()));
            bonusField.value(String.valueOf(ft.getBonus()));
        }
    }

    private LabeledField<?> payRow(String label, InputField field, String unit) {
        JPanel wrapper = new JPanel(new BorderLayout(8, 0));
        wrapper.setOpaque(false);
        wrapper.add(field, BorderLayout.CENTER);

        JLabel unitLabel = new JLabel(unit);
        unitLabel.setFont(UITheme.FONT_BODY);
        unitLabel.setForeground(UITheme.TEXT_DARK);
        wrapper.add(unitLabel, BorderLayout.EAST);

        LabeledField<JPanel> row = LabeledField.horizontal(label, wrapper);
        row.getLabel().setForeground(UITheme.TEXT_DARK);
        return row;
    }

    // Form submission
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