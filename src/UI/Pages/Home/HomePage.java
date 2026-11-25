package UI.Pages.Home;

import UI.Components.ComponentFactory;
import UI.Components.Divider;
import UI.Components.StatCard;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class HomePage extends AppPage {

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

        JPanel topRow = new JPanel(new GridLayout(1, 2, 10, 10));
        topRow.setOpaque(false);
        topRow.add(new StatCard("Employees", "24").icon(AppIcon.EMPLOYEE));
        topRow.add(new StatCard("Departments", "5").icon(AppIcon.DEPARTMENT));

        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomRow.setOpaque(false);
        bottomRow.add(new StatCard("Full-Time", "20").icon(AppIcon.FULL_TIME));
        bottomRow.add(new StatCard("Part-Time", "4").icon(AppIcon.PART_TIME));

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

        StatCard payrollCard = new StatCard("Total Monthly Payroll", "342,000 EGP")
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
    }
}