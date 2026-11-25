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

import UI.Components.ComponentFactory;
import Service.DepartmentService;

import UI.Popup;

public class EmployeesPage extends AppPage {
    public EmployeesPage(AppContext context) {
        super(context);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        EmployeeService employeeService = new EmployeeService();
        DepartmentService deptService = new DepartmentService();
        // * FOR TESTING
        // Department cs = deptService.addDepartment("CS");
        // employeeService.addEmployee(new FullTimeEmployee("Mohamed", cs, 18, "CEO",
        // 1000.0, 500.0));
        // employeeService.addEmployee(new PartTimeEmployee("Ahmed", it, 7, "slave",
        // 1.5, 80));
        List<Employee> employees = employeeService.getAllEmployees();
        // *
        EmployeesTable table = new EmployeesTable();
        table.model.loadEmployees(employees);

        JLabel title = new JLabel("Page 1", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        // JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 0));
        top.add(ComponentFactory.createInputField("", 10, newValue -> {
            table.model.loadEmployees(employeeService.searchByName(newValue));
        }));

        top.add(ComponentFactory.createComboBox(deptService.getAllDepartments().toArray(new Department[0]),
                newValue -> {
                    table.model.loadEmployees(employeeService.filterByDepartment(newValue.getName()));
                }));

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

    }
}