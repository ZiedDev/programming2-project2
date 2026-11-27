package UI.Pages.Departments;

import Model.Department;
import Model.Employee;
import UI.Components.AppButton;
import UI.Components.DataTable;
import UI.Components.Modal;
import UI.Components.InputField;
import UI.Components.LabeledField;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.AppIcon;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsPage extends AppPage {
    private static final String[] TABLE_COLUMNS = { "Department Name", "ID", "Employees", "Monthly Payroll" };

    private final DataTable<Department> table;
    private String currentSearch = "";

    public DepartmentsPage(AppContext context) {
        super(context);
        setLayout(new BorderLayout(0, 16));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        InputField searchField = new InputField("", 20)
                .onChange(query -> {
                    this.currentSearch = query;
                    refreshTable();
                });
        LabeledField<InputField> searchRow = LabeledField.horizontal("Search by Name:", searchField);

        topPanel.add(createHeader("Departments"));
        topPanel.add(searchRow);

        table = new DataTable<>(TABLE_COLUMNS, this::toRow);

        JPanel buttonsRow = new JPanel(new GridLayout(1, 3, 12, 0));
        buttonsRow.setOpaque(false);

        AppButton addBtn = AppButton.primary("Add Department")
                .icon(AppIcon.ADD)
                .alignLeft()
                .onClick(this::handleAdd);
        AppButton editBtn = AppButton.secondaryAlt("Edit Selected")
                .icon(AppIcon.EDIT)
                .alignLeft()
                .onClick(this::handleEdit);
        AppButton deleteBtn = AppButton.danger("Delete Selected")
                .icon(AppIcon.CANCEL)
                .alignLeft()
                .onClick(this::handleDelete);

        buttonsRow.add(addBtn);
        buttonsRow.add(editBtn);
        buttonsRow.add(deleteBtn);

        add(topPanel, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(buttonsRow, BorderLayout.SOUTH);

        refreshTable();
    }

    @Override
    public void onShow() {
        refreshTable();
    }

    private void handleAdd() {
        Modal.show(this, "Add Department", "Add Department", new DepartmentForm(""), deptName -> {
            context.departments().addDepartment(deptName);
            refreshTable();
            Modal.showInfo(this, "Department added successfully!");
        });
    }

    private void handleEdit() {
        Department selected = table.getSelectedItem();
        if (selected == null) {
            Modal.showWarning(this, "No Selection", "Please select a department to edit.");
            return;
        }

        Modal.show(this, "Edit Department", "Save Changes", AppIcon.EDIT,
                new DepartmentForm(selected.getName()), newName -> {
                    context.departments().updateDepartment(selected.getID(), newName, context.employees());
                    refreshTable();
                    Modal.showInfo(this, "Department updated successfully!");
                });
    }

    private void handleDelete() {
        Department selected = table.getSelectedItem();
        if (selected == null) {
            Modal.showWarning(this, "No Selection", "Please select a department to delete.");
            return;
        }

        boolean confirmed = Modal.confirm(
                this,
                "Confirm Delete",
                "Are you sure you want to delete '" + selected.getName() + "'?");

        if (confirmed) {
            try {
                context.departments().deleteDepartment(selected.getID(), context.employees());
                refreshTable();
                Modal.showInfo(this, "Department deleted successfully!");
            } catch (IllegalArgumentException ex) {
                Modal.showError(this, "Cannot Delete", ex.getMessage());
            }
        }
    }

    public void refreshTable() {
        List<Department> allDepts = context.departments().getAllDepartments();
        List<Department> filtered = new ArrayList<>();

        String query = currentSearch.trim().toLowerCase();
        for (Department d : allDepts) {
            if (query.isEmpty() || d.getName().toLowerCase().contains(query)) {
                filtered.add(d);
            }
        }

        table.setItems(filtered);
    }

    private Object[] toRow(Department dept) {
        int empCount = 0;
        double totalPayroll = 0.0;

        for (Employee emp : context.employees().getAllEmployees()) {
            if (emp.getDepartment() != null && dept.getID().equals(emp.getDepartment().getID())) {
                empCount++;
                totalPayroll += emp.getMonthlySalary();
            }
        }

        return new Object[] {
                dept.getName(),
                dept.getID(),
                empCount,
                String.format("%.2f EGP", totalPayroll)
        };
    }
}