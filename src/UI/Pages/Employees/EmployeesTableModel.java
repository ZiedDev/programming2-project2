package UI.Pages.Employees;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import Model.Employee;

public class EmployeesTableModel extends DefaultTableModel {
    private final List<Employee> displayedEmployees = new ArrayList<>();

    public EmployeesTableModel(String[] columns, int rows) {
        super(columns, rows);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addRow(Employee x) {
        this.displayedEmployees.add(x);
        this.addRow(new Object[] { x.getFullName(), x.getID(), x.getDepartment().getName(), x.getPosition(),
                x.getMonthlySalary() });
    }

    public void loadEmployees(List<Employee> employeeList) {
        this.setRowCount(0);
        this.displayedEmployees.clear();
        for (Employee employee : employeeList) {
            this.addRow(employee);
        }
    }

    public Employee getEmployeeAt(int row) {
        if (row < 0 || row >= displayedEmployees.size()) {
            return null;
        }
        return displayedEmployees.get(row);
    }
}
