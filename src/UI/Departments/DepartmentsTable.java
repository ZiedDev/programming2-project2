package UI.Departments;

import Model.Department;

import javax.swing.*;

public class DepartmentsTable {
    public final JScrollPane scrollPane;
    public final JTable table;
    public final DepartmentsTableModel model;

    public DepartmentsTable() {
        String[] columns = { "Department Name", "ID", "Employees", "Monthly Payroll" };
        model = new DepartmentsTableModel(columns, 0);

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(28);

        scrollPane = new JScrollPane(table);
    }

    public Department getSelectedDepartment() {
        int selectedRow = table.getSelectedRow();
        return model.getDepartmentAt(selectedRow);
    }
}