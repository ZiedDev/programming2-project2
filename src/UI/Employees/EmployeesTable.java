package UI.Employees;

import UI.UITheme.*;
import javax.swing.table.DefaultTableModel;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;

import javax.swing.JTable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import Service.EmployeeService;

import UI.Employees.EmployeesTableModel;

public class EmployeesTable {
    JScrollPane scrollPane;
    EmployeesTableModel model;

    public EmployeesTable() {
        String[] columns = { "Name", "ID", "Department", "Position", "Salary" };

        model = new EmployeesTableModel(columns, 0);
        JTable table = new JTable(model);
        scrollPane = new JScrollPane(table);
    }
}
