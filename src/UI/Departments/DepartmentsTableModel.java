package UI.Departments;

import Model.Department;
import Model.Employee;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsTableModel extends DefaultTableModel {
    private final List<Department> displayedDepartments = new ArrayList<>();

    public DepartmentsTableModel(String[] columns, int rows) {
        super(columns, rows);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void loadDepartments(List<Department> departments, List<Employee> employees) {
        this.setRowCount(0);
        this.displayedDepartments.clear();

        for (Department dept : departments) {
            int empCount = 0;
            double totalPayroll = 0.0;

            for (Employee emp : employees) {
                if (emp.getDepartment() != null && dept.getID().equals(emp.getDepartment().getID())) {
                    empCount++;
                    totalPayroll += emp.getMonthlySalary();
                }
            }

            this.displayedDepartments.add(dept);
            this.addRow(new Object[] {dept.getName(), dept.getID(), empCount, String.format("%.2f EGP", totalPayroll)});
        }
    }

    public Department getDepartmentAt(int row) {
        if (row < 0 || row >= displayedDepartments.size()) {
            return null;
        }
        return displayedDepartments.get(row);
    }
}