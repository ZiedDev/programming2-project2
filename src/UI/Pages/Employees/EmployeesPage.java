package UI.Pages.Employees;

import UI.Components.AppButton;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Shell.MainFrame;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

import Model.Department;
import Model.Employee;
import Service.EmployeeService;

import java.util.List;
import java.util.ArrayList;

import UI.Components.ComponentFactory;
import Service.DepartmentService;

import UI.Popup;

public class EmployeesPage extends AppPage {
    JComboBox deptsBox;
    EmployeeService employeeService = context.employees();
    DepartmentService deptService = context.departments();

    public EmployeesPage(AppContext context) {
        super(context);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        List<Employee> employees = employeeService.getAllEmployees();
        EmployeesTable table = new EmployeesTable();
        table.model.loadEmployees(employees);

        JLabel title = new JLabel("Page 1", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 0));
        top.setOpaque(false);

        top.add(ComponentFactory.createInputField("", 10, newValue -> {
            table.model.loadEmployees(employeeService.searchByName(newValue));
        }));

        List<String> options = new ArrayList<>(List.of(""));
        for (Department x : deptService.getAllDepartments()) {
            options.add(x.getName());
        }
        deptsBox = ComponentFactory.createComboBox(options.toArray(new String[0]),
                newValue -> {
                    table.model.loadEmployees(employeeService.filterByDepartment(newValue));
                });

        top.add(deptsBox);

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
        deptsBox.setModel(new DefaultComboBoxModel<>(options.toArray()));
    }
}