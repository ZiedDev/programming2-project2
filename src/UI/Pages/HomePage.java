package UI.Pages;

import Model.Employee;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import UI.Components.Divider;
import UI.Components.StatCard;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomePage extends AppPage {
    private final StatCard employeesCard;
    private final StatCard departmentsCard;
    private final StatCard fullTimeCard;
    private final StatCard partTimeCard;
    private final StatCard payrollCard;

    public HomePage(AppContext context) {
        super(context);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel header = createHeader("Welcome Back, Admin!");

        JLabel overviewLabel = new JLabel("Company Overview");
        overviewLabel.setFont(UITheme.FONT_SUBTITLE);
        overviewLabel.setForeground(UITheme.TEXT_LIGHT);
        overviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cards Grid
        JPanel cardsContainer = new JPanel(new GridLayout(2, 1, 10, 10));
        cardsContainer.setOpaque(false);
        cardsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardsContainer.setPreferredSize(new Dimension(0, 280));
        cardsContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));

        employeesCard = new StatCard("Employees", 0).icon(AppIcon.EMPLOYEE);
        departmentsCard = new StatCard("Departments", 0).icon(AppIcon.DEPARTMENT);
        fullTimeCard = new StatCard("Full-Time", 0).icon(AppIcon.FULL_TIME);
        partTimeCard = new StatCard("Part-Time", 0).icon(AppIcon.PART_TIME);

        JPanel topRow = new JPanel(new GridLayout(1, 2, 10, 10));
        topRow.setOpaque(false);
        topRow.add(employeesCard);
        topRow.add(departmentsCard);

        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomRow.setOpaque(false);
        bottomRow.add(fullTimeCard);
        bottomRow.add(partTimeCard);

        cardsContainer.add(topRow);
        cardsContainer.add(bottomRow);

        // Separator
        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Payroll
        JPanel payrollContainer = new JPanel(new GridLayout(1, 1, 10, 10));
        payrollContainer.setOpaque(false);
        payrollContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        payrollContainer.setPreferredSize(new Dimension(0, 135));
        payrollContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));

        payrollCard = new StatCard("Total Monthly Payroll", "0.00 EGP")
                .icon(AppIcon.MONEY, IconColor.ACCENT)
                .accentBackground(UITheme.PRIMARY);
        payrollContainer.add(payrollCard);

        // Add to HomePage
        add(header);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(overviewLabel);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(cardsContainer);
        add(separator);
        add(payrollContainer);
        add(Box.createVerticalGlue()); // Absorbs remaining space at the bottom

        refresh();
    }

    @Override
    public void onShow() {
        refresh();
    }

    private void refresh() {
        List<Employee> employees = context.employees().getAllEmployees();
        int fullTimeCount = 0;
        int partTimeCount = 0;
        double totalPayroll = 0.0;

        for (Employee emp : employees) {
            if (emp instanceof FullTimeEmployee) {
                fullTimeCount++;
            } else if (emp instanceof PartTimeEmployee) {
                partTimeCount++;
            }
            totalPayroll += emp.getMonthlySalary();
        }

        employeesCard.setValue(employees.size());
        departmentsCard.setValue(context.departments().getAllDepartments().size());
        fullTimeCard.setValue(fullTimeCount);
        partTimeCard.setValue(partTimeCount);
        payrollCard.setValue(String.format("%,.2f EGP", totalPayroll));
    }
}