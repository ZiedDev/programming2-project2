package UI.Employees;

import UI.MainFrame;
import UI.UITheme.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import Service.EmployeeService;
import java.util.List;
import UI.ComponentFactory;
import Service.DepartmentService;

import UI.Popup;

import UI.MultipleInputField;

public class EmployeesPage extends JPanel {
    public EmployeesPage(Consumer<String> navigate) {
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

        JButton backBtn = new JButton("Back to Main");
        backBtn.addActionListener(e -> navigate.accept(MainFrame.HOME));

        // JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton addBtn = ComponentFactory.createPrimaryButton("Add New Employee", AppIcon.ADD, IconColor.ACCENT);
        addBtn.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 0));
        top.add(ComponentFactory.createInputField("Name", 10, newValue -> {
            table.model.loadEmployees(employeeService.searchByName(newValue));
        }));

        top.add(ComponentFactory.createComboBox(deptService.getAllDepartments().toArray(), newValue -> {
            table.model.loadEmployees(employeeService.filterByDepartment(newValue));
        }));

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        MultipleInputField fields = ComponentFactory.createMultipleInputsPanel(new String[] { "Name", "Age" });

        Popup popUp = ComponentFactory.createPopup("Add new employee", parentFrame); // example // usage
        popUp.addBodyComponent(fields);

        popUp.addBodyComponent(
                ComponentFactory.createComboBoxPanelDesc("department", deptService.getAllDepartments().toArray(),
                        newValue -> {
                            // TODO ADD LOGIC
                        }));

        popUp.addBodyComponent(
                ComponentFactory.createComboBoxPanelDesc("Type", employeeService.getEmployeeTypes(), newValue -> {
                    // TODO ADD LOGIC
                }));

        JButton addDialogBtn = ComponentFactory.createPrimaryButton("Add Employee", AppIcon.ADD, IconColor.ACCENT);
        popUp.addFooterComponent(addDialogBtn);
        addDialogBtn.setHorizontalAlignment(SwingConstants.LEFT);
        addDialogBtn.addActionListener(e -> employeeService.addEmployee(makeEmployee(fields)));

        addBtn.addActionListener(e -> popUp.showPopup(parentFrame));
        add(addBtn, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(table.scrollPane, BorderLayout.CENTER);
    }

    private Employee makeEmployee(MultipleInputField fields) {
        System.out.println(fields.getValue("Name"));
        Department cs = new Department("CS"); //TESTING // TODO: REMOVE LATER
        return new FullTimeEmployee("Mohamed", cs, 18, "CEO", 1000.0, 500.0);//TESTING //TODO: REMOVE LATER
    }
}