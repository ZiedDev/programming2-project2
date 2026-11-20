package UI;

import UI.home.HomePage;
import UI.Employees.EmployeesPage;
import UI.Departments.DepartmentsPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String HOME = "HOME";
    public static final String EMPLOYEES = "EMPLOYEES";
    public static final String DEPARTMENTS = "DEPARTMENTS";

    private final CardLayout cardLayout;
    private final JPanel container;

    private JButton homeBtn;
    private JButton employeesBtn;
    private JButton departmentsBtn;

    public MainFrame() {
        this.setTitle("Employees Management Portal");
        // this.setResizable(false);
        this.setResizable(true);
        this.setSize(1024, 768);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setBackground(UITheme.BG_DARK);

        // Adding sub-pages to the container
        container.add(new HomePage(this::switchPage), HOME);
        container.add(new EmployeesPage(this::switchPage), EMPLOYEES);
        container.add(new DepartmentsPage(this::switchPage), DEPARTMENTS);

        root.add(container, BorderLayout.CENTER);
        this.setContentPane(root);

        switchPage(HOME);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(1024, 768));
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.BG_BASE);
        sidebar.setPreferredSize(new Dimension(240, 768));
        sidebar.setBorder(new EmptyBorder(24, 16, 24, 16));

        // Portal Title
        JLabel title = new JLabel("Portal");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_LIGHT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 32)));

        // Navigation Buttons
        homeBtn = ComponentFactory.createSidebarButton("Home");
        employeesBtn = ComponentFactory.createSidebarButton("Employees");
        departmentsBtn = ComponentFactory.createSidebarButton("Departments");

        homeBtn.addActionListener(e -> this.switchPage(HOME));
        employeesBtn.addActionListener(e -> this.switchPage(EMPLOYEES));
        departmentsBtn.addActionListener(e -> this.switchPage(DEPARTMENTS));

        sidebar.add(homeBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // empty area
        sidebar.add(employeesBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // empty area
        sidebar.add(departmentsBtn);

        sidebar.add(Box.createVerticalGlue()); // creates an empty area to push (Log out & Exit) to the bottom

        JButton logoutBtn = ComponentFactory.createSecondaryButton("Log out");
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.addActionListener(e -> {});

        JButton exitBtn = ComponentFactory.createDangerButton("Exit");
        exitBtn.setHorizontalAlignment(SwingConstants.LEFT);
        exitBtn.addActionListener(e -> System.exit(0));

        sidebar.add(logoutBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // empty area
        sidebar.add(exitBtn);

        return sidebar;
    }

    public void switchPage(String pageName) {
        cardLayout.show(container, pageName);
        ComponentFactory.setSidebarButtonActive(homeBtn, HOME.equals(pageName));
        ComponentFactory.setSidebarButtonActive(employeesBtn, EMPLOYEES.equals(pageName));
        ComponentFactory.setSidebarButtonActive(departmentsBtn, DEPARTMENTS.equals(pageName));
    }

}
