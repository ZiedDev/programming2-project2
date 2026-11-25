package UI.Pages.Departments;

import Model.Department;
import Service.DepartmentService;
import Service.EmployeeService;
import UI.Components.AppButton;
import UI.Components.ComponentFactory;
import UI.Components.Divider;
import UI.Components.StatCard;
import UI.Pages.Employees.EmployeeForm;
import UI.Pages.Employees.FullTimeForm;
import UI.Pages.Employees.PartTimeForm;
import UI.Popup;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.UITheme;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DepartmentsPage extends AppPage {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    private final DepartmentsTable table;
    private final StatCard totalDeptsCard;
    private final StatCard totalEmpsCard;
    private String currentSearch = "";

    public DepartmentsPage(AppContext context) {
        super(context);
        this.departmentService = new DepartmentService();
        this.employeeService = new EmployeeService();

        this.setLayout(new BorderLayout(0, 16));
        this.setBackground(UITheme.BG_DARK);
        this.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JLabel title = new JLabel("Departments");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_LIGHT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel statsRow = new JPanel(new GridLayout(1, 2, 10, 0));
        statsRow.setOpaque(false);
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        totalDeptsCard = new StatCard("Total Departments", 0).icon(AppIcon.DEPARTMENT, IconColor.DARK);
        totalEmpsCard  = new StatCard("Total Employees", 0).icon(AppIcon.EMPLOYEE, IconColor.DARK);
        statsRow.add(totalDeptsCard);
        statsRow.add(totalEmpsCard);

        JPanel searchRow = ComponentFactory.createInputField("Search by Name:", "", 20, query -> {
            this.currentSearch = query;
            refreshTable();
        });
        searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(separator);
        topPanel.add(statsRow);
        topPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        topPanel.add(searchRow);

        table = new DepartmentsTable();

        JPanel buttonsRow = new JPanel(new GridLayout(1, 3, 12, 0));
        buttonsRow.setOpaque(false);

        AppButton addBtn = AppButton.primary("Add Department")
                .icon(AppIcon.ADD, IconColor.DARK)
                .alignLeft()
                .onClick(this::handleAdd);
        AppButton editBtn = AppButton.secondary("Edit Selected")
                .icon(AppIcon.EDIT, IconColor.LIGHT)
                .alignLeft()
                .onClick(this::handleEdit);
        AppButton deleteBtn = AppButton.danger("Delete Selected")
                .icon(AppIcon.CANCEL, IconColor.DANGER)
                .alignLeft()
                .onClick(this::handleDelete);

        buttonsRow.add(addBtn);
        buttonsRow.add(editBtn);
        buttonsRow.add(deleteBtn);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(table.scrollPane, BorderLayout.CENTER);
        this.add(buttonsRow, BorderLayout.SOUTH);

        refreshTable();
    }

    private void handleAdd() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        DepartmentForm form = new DepartmentForm("");

        Popup.showForm(parent, "Add Department", "Add", form, deptName -> {
            departmentService.addDepartment(deptName);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Department added successfully!");
        });
    }

    private void handleEdit() {
        Department selected = table.getSelectedDepartment();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a department to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        DepartmentForm form = new DepartmentForm(selected.getName());

        Popup.showForm(parent, "Edit Department", "Save Changes", form, newName -> {
            departmentService.updateDepartment(selected.getID(), newName, employeeService);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Department updated successfully!");
        });
    }

    private void handleDelete() {
        Department selected = table.getSelectedDepartment();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a department to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete '" + selected.getName() + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                departmentService.deleteDepartment(selected.getID(), employeeService);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Department deleted successfully!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot Delete", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshTable() {
        List<Department> allDepts = departmentService.getAllDepartments();
        List<Department> filtered = new ArrayList<>();

        String query = currentSearch.trim().toLowerCase();
        for (Department d : allDepts) {
            if (query.isEmpty() || d.getName().toLowerCase().contains(query)) {
                filtered.add(d);
            }
        }

        table.model.loadDepartments(filtered, employeeService.getAllEmployees());
        totalDeptsCard.setValue(allDepts.size());
        totalEmpsCard.setValue(context.employees().getAllEmployees().size());
    }
}