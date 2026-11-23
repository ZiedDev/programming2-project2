package UI;

import UI.Departments.DepartmentsPage;
import UI.Employees.EmployeesPage;
import UI.Login.LoginPage;
import UI.home.HomePage;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String HOME = "HOME";
    public static final String EMPLOYEES = "EMPLOYEES";
    public static final String DEPARTMENTS = "DEPARTMENTS";
    public static final String LOGIN = "LOGIN";
    private static final String APP = "APP";

    private final CardLayout outerLayout;
    private final JPanel outerRoot;
    private final CardLayout cardLayout;
    private final JPanel container;
    private final Sidebar sidebar;

    public MainFrame() {
        this.setTitle("Employees Management Portal");
        this.setResizable(true);
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        outerLayout = new CardLayout();
        outerRoot = new JPanel(outerLayout);

        // Logged-in shell: one sidebar on the left, inner pages in the center
        JPanel appShell = new JPanel(new BorderLayout());
        sidebar = new Sidebar(this::switchPage);
        appShell.add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setBackground(UITheme.BG_DARK);
        container.add(new HomePage(this::switchPage), HOME);
        container.add(new EmployeesPage(this::switchPage), EMPLOYEES);
        container.add(new DepartmentsPage(this::switchPage), DEPARTMENTS);
        appShell.add(container, BorderLayout.CENTER);

        outerRoot.add(new LoginPage(this::switchPage), LOGIN);
        outerRoot.add(appShell, APP);

        this.setContentPane(outerRoot);
        switchPage(LOGIN);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(1024, 768));
    }

    public void switchPage(String pageName) {
        if (LOGIN.equals(pageName)) {
            outerLayout.show(outerRoot, LOGIN);
            return;
        }
        outerLayout.show(outerRoot, APP);
        cardLayout.show(container, pageName);
        sidebar.setActivePage(pageName);
    }
}