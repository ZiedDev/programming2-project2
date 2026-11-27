package UI.Shell;

import Service.DepartmentService;
import Service.EmployeeService;
import UI.Pages.DepartmentsPage;
import UI.Pages.EmployeesPage;
import UI.Pages.HomePage;
import UI.Pages.LoginPage;
import UI.Theme.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final JPanel root;
    private final CardLayout contentLayout;
    private final JPanel contentContainer;
    private final Sidebar sidebar;
    private final Map<Route, AppPage> pages = new EnumMap<>(Route.class);

    public MainFrame() {
        setTitle("Employees Management Portal");
        setResizable(true);
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        AppContext context = new AppContext(
                new DepartmentService(),
                new EmployeeService(),
                this::navigate);

        root = new JPanel(new BorderLayout());
        sidebar = new Sidebar(this::navigate);

        contentLayout = new CardLayout();
        contentContainer = new JPanel(contentLayout);
        contentContainer.setBackground(UITheme.BG_DARK);

        registerPage(Route.LOGIN, new LoginPage(context));
        registerPage(Route.HOME, new HomePage(context));
        registerPage(Route.EMPLOYEES, new EmployeesPage(context));
        registerPage(Route.DEPARTMENTS, new DepartmentsPage(context));

        root.add(sidebar, BorderLayout.WEST);
        root.add(contentContainer, BorderLayout.CENTER);

        setContentPane(root);
        navigate(Route.LOGIN);
        setVisible(true);
        setMinimumSize(new Dimension(1024, 768));
    }

    private void registerPage(Route route, AppPage page) {
        pages.put(route, page);
        contentContainer.add(page, route.name());
    }

    public void navigate(Route route) {
        // hide sidebar in login page
        if (route == Route.LOGIN) {
            sidebar.setVisible(false);
        } else {
            sidebar.setVisible(true);
            sidebar.setActiveRoute(route);
        }

        contentLayout.show(contentContainer, route.name());

        // redraws the root (since in the login the sidebar is hidden so the root width updates)
        root.revalidate();
        root.repaint();

        // triggers the refresh hook for the page
        AppPage activePage = pages.get(route);
        if (activePage != null) {
            activePage.onShow();
        }
    }
}