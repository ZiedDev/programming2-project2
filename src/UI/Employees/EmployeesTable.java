package UI.Employees;
import javax.swing.JTable;
import javax.swing.JScrollPane;
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
