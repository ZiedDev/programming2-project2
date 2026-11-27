package UI.Pages.Employees;

import UI.Components.*;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.AppIcon;

import javax.swing.*;
import java.awt.*;
import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import java.util.List;
import java.util.ArrayList;

public class EmployeesPage extends AppPage {
    private static final String[] TABLE_COLUMNS = { "Name", "ID", "Department", "Position", "Salary" };
    private final DataTable<Employee> table;
    private String currentSearch = "";
    private String currDepartmentName = "";
    SelectField<String> deptsBox;

    public EmployeesPage(AppContext context) {
        super(context);
        setLayout(new BorderLayout(0, 16));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JPanel searchParams = new JPanel(new GridLayout(1, 2, 10, 0));
        searchParams.setOpaque(false);
        searchParams.setAlignmentX(LEFT_ALIGNMENT);

        table = new DataTable<>(TABLE_COLUMNS, this::toRow);

        InputField searchField = new InputField("", 10)
                .onChange(query -> {
                    this.currentSearch = query;
                    refreshTable();
                });
        LabeledField<InputField> employeeSearchRow = LabeledField.vertical("Search by name", searchField);

        List<String> options = new ArrayList<>(List.of(""));
        for (Department x : context.departments().getAllDepartments()) {
            options.add(x.getName());
        }
        deptsBox = new SelectField<>(options).onChange(newValue -> {
            this.currDepartmentName = newValue;
            refreshTable();
        });

        JPanel buttonsRow = new JPanel(new GridLayout(1, 3, 12, 0));
        buttonsRow.setOpaque(false);

        AppButton addBtn = AppButton.primary("Add New Employee")
                .icon(AppIcon.ADD)
                .alignLeft()
                .onClick(this::handleAdd);
        AppButton editBtn = AppButton.secondaryAlt("Edit Selected")
                .icon(AppIcon.EDIT)
                .alignLeft()
                .onClick(this::handleEdit);
        AppButton deleteBtn = AppButton.danger("Delete Selected")
                .icon(AppIcon.CANCEL)
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
        add(table, BorderLayout.CENTER);

        refresh();
    }

    public void onShow() {
        refresh();
    }

    // what to update when the page is refreshed
    private void refresh() {
        List<String> options = new ArrayList<>(List.of(""));
        for (Department x : context.departments().getAllDepartments()) {
            options.add(x.getName());
        }
        deptsBox.options(options);
    }

    private void handleAdd() {
        List<Department> depts = context.departments().getAllDepartments();
        EmployeeForm form = new EmployeeForm(List.of(new FullTimeForm(depts), new PartTimeForm(depts)));
        Modal.show(this, "Add New Employee", "Add Employee", form, emp -> {
            context.employees().addEmployee(emp);
            refreshTable();
            Modal.showInfo(this, "Employee added successfully!");
        });
    }

    private void handleEdit() {
        Employee selected = table.getSelectedItem();
        if (selected == null) {
            Modal.showWarning(this, "No Selection", "Please select an employee to edit.");
            return;
        }

        List<Department> depts = context.departments().getAllDepartments();
        EmployeeForm form = new EmployeeForm(
                List.of(new FullTimeForm(depts, selected), new PartTimeForm(depts, selected)),
                selected);

        Modal.show(this, "Edit Employee", "Save Changes", AppIcon.EDIT, form, updated -> {
            if (updated instanceof FullTimeEmployee ft) {
                context.employees().updateEmployee(
                        selected.getID(),
                        ft.getFullName(),
                        ft.getDepartment(),
                        ft.getAge(),
                        ft.getPosition(),
                        ft.getBasicSalary(),
                        ft.getBonus());
            } else if (updated instanceof PartTimeEmployee pt) {
                context.employees().updateEmployee(
                        selected.getID(),
                        pt.getFullName(),
                        pt.getDepartment(),
                        pt.getAge(),
                        pt.getPosition(),
                        pt.getHourlyRate(),
                        pt.getHoursPerMonth());
            }
            refreshTable();
            Modal.showInfo(this, "Employee updated successfully!");
        });
    }

    private void handleDelete() {
        Employee selected = table.getSelectedItem();
        if (selected == null) {
            Modal.showWarning(this, "No Selection", "Please select an employee to delete.");
            return;
        }

        boolean confirmed = Modal.confirm(
                this,
                "Confirm Delete",
                "Are you sure you want to delete '" + selected.getFullName() + "'?");

        if (confirmed) {
            try {
                context.employees().deleteEmployee(selected.getID());
                refreshTable();
                Modal.showInfo(this, "Employee deleted successfully!");
            } catch (IllegalArgumentException ex) {
                Modal.showError(this, "Cannot Delete", ex.getMessage());
            }
        }
    }

    public void refreshTable() {
        List<Employee> allEmployees = context.employees().getAllEmployees();
        List<Employee> filtered = new ArrayList<>();
        String nameQuery = currentSearch.trim().toLowerCase();
        String deptQuery = currDepartmentName.trim().toLowerCase();
        for (Employee emp : allEmployees) {
            if ((nameQuery.isEmpty() || emp.getFullName().toLowerCase().contains(nameQuery))
                    && (deptQuery.isEmpty() || emp.getDepartment().getName().toLowerCase().contains(deptQuery))) {
                filtered.add(emp);
            }
        }
        table.setItems(filtered);
    }

    private Object[] toRow(Employee emp) {
        return new Object[] {
                emp.getFullName(),
                emp.getID(),
                emp.getDepartment().getName(),
                emp.getPosition(),
                emp.getMonthlySalary()
        };
    }
}