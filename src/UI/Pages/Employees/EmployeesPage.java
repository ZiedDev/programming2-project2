package UI.Pages.Employees;

import UI.Components.*;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import Service.EmployeeService;
import Service.DepartmentService;
import java.util.List;
import java.util.ArrayList;
import UI.Popup;

public class EmployeesPage extends AppPage {
    SelectField<String> deptsBox;
    EmployeeService employeeService = context.employees();
    DepartmentService deptService = context.departments();
    EmployeesTable table;
    public EmployeesPage(AppContext context) {
        super(context);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        List<Employee> employees = employeeService.getAllEmployees();
        table = new EmployeesTable();
        table.model.loadEmployees(employees);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JPanel searchParams = new JPanel(new GridLayout(1, 2, 10, 0));
        searchParams.setOpaque(false);
        searchParams.setAlignmentX(LEFT_ALIGNMENT);

        InputFieldNew searchField = new InputFieldNew("", 10)
                .onChange(newValue -> table.model.loadEmployees(employeeService.searchByName(newValue)));
        LabeledField<InputFieldNew> employeeSearchRow = LabeledField.vertical("Search by name", searchField);

        List<String> options = new ArrayList<>(List.of(""));
        for (Department x : deptService.getAllDepartments()) {
            options.add(x.getName());
        }
        deptsBox = new SelectField<>(options).onChange(
                newValue -> table.model.loadEmployees(employeeService.filterByDepartment(newValue)));

        JPanel buttonsRow = new JPanel(new GridLayout(1, 3, 12, 0));
        buttonsRow.setOpaque(false);

        AppButton addBtn = AppButton.primary("Add New Employee")
                .icon(AppIcon.ADD, IconColor.DARK)
                .alignLeft()
                .onClick(this::handleAdd);
        AppButton editBtn = AppButton.secondaryAlt("Edit Selected")
                .icon(AppIcon.EDIT, IconColor.LIGHT)
                .alignLeft()
                .onClick(this::handleEdit);
        AppButton deleteBtn = AppButton.danger("Delete Selected")
                .icon(AppIcon.CANCEL, IconColor.DANGER)
                .alignLeft()
                .onClick(this::handleDelete);

        buttonsRow.add(addBtn);
        buttonsRow.add(editBtn);
        buttonsRow.add(deleteBtn);

        searchParams.add(employeeSearchRow);
        searchParams.add(LabeledField.vertical("Department", deptsBox));

        top.add(createHeader("Employees"));
        top.add(searchParams);

        add(buttonsRow, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);
        add(table.scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void onShow() {
        refresh();
    }

    // what to update when the page is refreshed
    private void refresh() {
        List<String> options = new ArrayList<>(List.of(""));
        for (Department x : deptService.getAllDepartments()) {
            options.add(x.getName());
        }
        deptsBox.options(options);
    }

    private void handleAdd() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Department> depts = deptService.getAllDepartments();
        EmployeeForm form = new EmployeeForm(List.of(new FullTimeForm(depts), new PartTimeForm(depts)));
        Popup.showForm(parent, "Add new Employee", "Add Employee", form, emp -> {
            employeeService.addEmployee(emp);
            table.model.loadEmployees(employeeService.getAllEmployees());
        });
    }

    private void handleEdit() {
        Employee selected = table.getSelectedEmployee();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Department> depts = deptService.getAllDepartments();
        EmployeeForm form = new EmployeeForm(
                List.of(new FullTimeForm(depts, selected), new PartTimeForm(depts, selected)),
                selected);

        Popup.showForm(parent, "Edit Employee", "Save Changes", form, updated -> {
            if (updated instanceof FullTimeEmployee ft) {
                employeeService.updateEmployee(
                        selected.getID(),
                        ft.getFullName(),
                        ft.getDepartment(),
                        ft.getAge(),
                        ft.getPosition(),
                        ft.getBasicSalary(),
                        ft.getBonus());
            } else if (updated instanceof PartTimeEmployee pt) {
                employeeService.updateEmployee(
                        selected.getID(),
                        pt.getFullName(),
                        pt.getDepartment(),
                        pt.getAge(),
                        pt.getPosition(),
                        pt.getHourlyRate(),
                        pt.getHoursPerMonth());
            }
            table.model.loadEmployees(employeeService.getAllEmployees());
            JOptionPane.showMessageDialog(this, "Employee updated successfully!");
        });
    }

    private void handleDelete() {
        Employee selected = table.getSelectedEmployee();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete '" + selected.getFullName() + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                employeeService.deleteEmployee(selected.getID());
                table.model.loadEmployees(employeeService.getAllEmployees());
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot Delete", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}