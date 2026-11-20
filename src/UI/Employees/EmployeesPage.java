package UI.Employees;

import UI.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;
import Model.Employee;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import Service.EmployeeService;
import Model.Department;
import java.util.List;
import java.util.ArrayList;
import UI.UITheme.*;
import javax.swing.table.DefaultTableModel;

public class EmployeesPage extends JPanel {
    public EmployeesPage(Consumer<String> navigate) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));


        EmployeeService employeeService = new EmployeeService();
        //* FOR TESTING
        Department cs = new Department("CS");
        Department it = new Department("IT");
        employeeService.addEmployee(new FullTimeEmployee("Mohamed", cs, 18, "CEO", 1000.0, 500.0));
        employeeService.addEmployee(new PartTimeEmployee("Ahmed", it, 7, "slave", 1.5, 80));
        List<Employee> employees = employeeService.getAllEmployees();
        //*
        EmployeesTable table = new EmployeesTable();
        table.model.loadEmployees(employees);

        JLabel title = new JLabel("Page 1", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton backBtn = new JButton("Back to Main");
        backBtn.addActionListener(e -> navigate.accept(MainFrame.HOME));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.add(backBtn);

        //JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        add(bottom, BorderLayout.SOUTH);
        add(table.scrollPane, BorderLayout.CENTER);
    }
}