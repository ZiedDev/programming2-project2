package UI.Employees;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import Model.Employee;

public class EmployeesTableModel extends DefaultTableModel {
    public EmployeesTableModel(String[] columns, int rows) {
        super(columns, rows);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addRow(Employee x) {
        this.addRow(new Object[] { x.getFullName(), x.getID(), x.getDepartment().getName(), x.getPosition(),
                x.getMonthlySalary() });
    }

    public void loadEmployees(List<Employee> employeeList) {
        this.setRowCount(0);
        for (Employee employee : employeeList) {
            this.addRow(employee);
        }
    }
}
