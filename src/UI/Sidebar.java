package UI;

import UI.UITheme.AppIcon;
import UI.UITheme.IconColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class Sidebar extends JPanel {
    private final JButton homeBtn;
    private final JButton employeesBtn;
    private final JButton departmentsBtn;

    public Sidebar(Consumer<String> onNavigate) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(UITheme.BG_BASE);
        this.setPreferredSize(new Dimension(240, 768));
        this.setBorder(new EmptyBorder(24, 16, 24, 16));

        JLabel title = new JLabel("Employees");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_LIGHT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("Management");
        title2.setFont(UITheme.FONT_TITLE);
        title2.setForeground(UITheme.TEXT_LIGHT);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title3 = new JLabel("Portal");
        title3.setFont(UITheme.FONT_TITLE);
        title3.setForeground(UITheme.TEXT_LIGHT);
        title3.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(title);
        this.add(title2);
        this.add(title3);
        this.add(ComponentFactory.createHorizontalSeparator());

        homeBtn = ComponentFactory.createSidebarButton("Home", AppIcon.HOME);
        employeesBtn = ComponentFactory.createSidebarButton("Employees", AppIcon.EMPLOYEE);
        departmentsBtn = ComponentFactory.createSidebarButton("Departments", AppIcon.DEPARTMENT);

        homeBtn.addActionListener(e -> onNavigate.accept(MainFrame.HOME));
        employeesBtn.addActionListener(e -> onNavigate.accept(MainFrame.EMPLOYEES));
        departmentsBtn.addActionListener(e -> onNavigate.accept(MainFrame.DEPARTMENTS));

        this.add(homeBtn);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(employeesBtn);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(departmentsBtn);

        this.add(Box.createVerticalGlue());
        this.add(ComponentFactory.createHorizontalSeparator());

        JButton logoutBtn = ComponentFactory.createSecondaryButton("Log out", AppIcon.LOGOUT);
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.addActionListener(e -> onNavigate.accept(MainFrame.LOGIN));

        JButton exitBtn = ComponentFactory.createDangerButton("Exit", AppIcon.CANCEL, IconColor.DANGER);
        exitBtn.setHorizontalAlignment(SwingConstants.LEFT);
        exitBtn.addActionListener(e -> System.exit(0));

        this.add(logoutBtn);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(exitBtn);
    }

    public void setActivePage(String pageName) {
        ComponentFactory.setSidebarButtonActive(homeBtn, MainFrame.HOME.equals(pageName));
        ComponentFactory.setSidebarButtonActive(employeesBtn, MainFrame.EMPLOYEES.equals(pageName));
        ComponentFactory.setSidebarButtonActive(departmentsBtn, MainFrame.DEPARTMENTS.equals(pageName));
    }
}