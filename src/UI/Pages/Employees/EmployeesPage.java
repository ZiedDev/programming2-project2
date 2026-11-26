package UI.Pages.Employees;

import UI.Components.*;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.AppIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Model.Department;
import Model.Employee;
import Service.EmployeeService;
import Service.DepartmentService;
import java.util.List;
import java.util.ArrayList;
import UI.Popup;

public class EmployeesPage extends AppPage {
    SelectField<String> deptsBox;
    EmployeeService employeeService = context.employees();
    DepartmentService deptService = context.departments();

    public EmployeesPage(AppContext context) {
        super(context);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        List<Employee> employees = employeeService.getAllEmployees();
        EmployeesTable table = new EmployeesTable();
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
            
            
            AppButton addBtn = AppButton.primary("Add New Employee")
            .icon(AppIcon.ADD)
            .alignLeft()
            .onClick(() -> {
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
                List<Department> depts = deptService.getAllDepartments();
                EmployeeForm form = new EmployeeForm(List.of(new FullTimeForm(depts), new PartTimeForm(depts)));
                Popup.showForm(parent, "Add new Employee", "Add Employee", form, emp -> {
                    employeeService.addEmployee(emp);
                    table.model.loadEmployees(employeeService.getAllEmployees());
                });
            });
            
            
        searchParams.add(employeeSearchRow);
        searchParams.add(LabeledField.vertical("Department", deptsBox));

        top.add(createHeader("Employees"));
        top.add(searchParams);

        add(addBtn, BorderLayout.SOUTH);
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
}