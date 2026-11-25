package UI.Shell;

import UI.Components.Divider;
import UI.Components.AppButton;
import UI.Components.SidebarButton;
import UI.Theme.UITheme;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class Sidebar extends JPanel {
    private final Map<Route, SidebarButton> navButtons = new EnumMap<>(Route.class);

    public Sidebar(Consumer<Route> onNavigate) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UITheme.BG_BASE);
        setPreferredSize(new Dimension(240, 768));
        setBorder(new EmptyBorder(24, 16, 24, 16));

        addTitleLine("Employees");
        addTitleLine("Management");
        addTitleLine("Portal");
        add(Divider.horizontal());

        addNavButton(Route.HOME, "Home", AppIcon.HOME, onNavigate);
        add(Box.createRigidArea(new Dimension(0, 10)));
        addNavButton(Route.EMPLOYEES, "Employees", AppIcon.EMPLOYEE, onNavigate);
        add(Box.createRigidArea(new Dimension(0, 10)));
        addNavButton(Route.DEPARTMENTS, "Departments", AppIcon.DEPARTMENT, onNavigate);

        add(Box.createVerticalGlue());
        add(Divider.horizontal());

        AppButton logoutBtn = AppButton.secondary("Log out")
                .icon(AppIcon.LOGOUT)
                .alignLeft()
                .onClick(() -> onNavigate.accept(Route.LOGIN));

        AppButton exitBtn = AppButton.danger("Exit")
                .icon(AppIcon.CANCEL, IconColor.DANGER)
                .alignLeft()
                .onClick(() -> System.exit(0));

        add(logoutBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(exitBtn);
    }

    private void addTitleLine(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.FONT_TITLE);
        label.setForeground(UITheme.TEXT_LIGHT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }

    private void addNavButton(Route route, String label, AppIcon icon, Consumer<Route> onNavigate) {
        SidebarButton btn = new SidebarButton(label)
                .icon(icon)
                .onClick(() -> onNavigate.accept(route));
        navButtons.put(route, btn);
        add(btn);
    }

    public void setActiveRoute(Route activeRoute) {
        for (Map.Entry<Route, SidebarButton> entry : navButtons.entrySet()) {
            entry.getValue().setActive(entry.getKey() == activeRoute);
        }
    }
}