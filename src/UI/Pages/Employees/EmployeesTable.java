package UI.Pages.Employees;

import Model.Employee;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class EmployeesTable {
    public final JScrollPane scrollPane;
    public final JTable table;
    public final EmployeesTableModel model;

    public EmployeesTable() {
        String[] columns = { "Name", "ID", "Department", "Position", "Salary" };

        model = new EmployeesTableModel(columns, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(table);
    }

    public Employee getSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        return model.getEmployeeAt(selectedRow);
    }
}
