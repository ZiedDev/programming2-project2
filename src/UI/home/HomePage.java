package UI.home;

import UI.ComponentFactory;
import UI.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class HomePage extends JPanel {

    public HomePage(Consumer<String> navigate) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(UITheme.BG_DARK);
        this.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Title
        JLabel welcomeLabel = new JLabel("Welcome Back, Admin!");
        welcomeLabel.setFont(UITheme.FONT_TITLE);
        welcomeLabel.setForeground(UITheme.TEXT_LIGHT);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Separator
        JPanel separator = ComponentFactory.createHorizontalSeparator();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Section Subtitle
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
        topRow.add(ComponentFactory.createStatCard("Employees", "24", UITheme.AppIcon.EMPLOYEE, UITheme.IconColor.DARK));
        topRow.add(ComponentFactory.createStatCard("Departments", "5", UITheme.AppIcon.DEPARTMENT, UITheme.IconColor.DARK));

        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomRow.setOpaque(false); // Keep transparent so the 10px gap shows!
        bottomRow.add(ComponentFactory.createStatCard("Full-Time", "20", UITheme.AppIcon.FULL_TIME, UITheme.IconColor.DARK));
        bottomRow.add(ComponentFactory.createStatCard("Part-Time", "4", UITheme.AppIcon.PART_TIME, UITheme.IconColor.DARK));

        cardsContainer.add(topRow);
        cardsContainer.add(bottomRow);

        // Separator
        JPanel separator2 = ComponentFactory.createHorizontalSeparator();
        separator2.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Payroll
        JPanel payrollContainer = new JPanel(new GridLayout(1, 1, 10, 10));
        payrollContainer.setOpaque(false);
        payrollContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        payrollContainer.setPreferredSize(new Dimension(0, 135));
        payrollContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));

        JPanel payrollCard = ComponentFactory.createStatCard("Total Monthly Payroll", "342,000 EGP", UITheme.AppIcon.MONEY, UITheme.IconColor.ACCENT);
        payrollCard.setBackground(UITheme.PRIMARY);
        payrollContainer.add(payrollCard);

        // Add to HomePage
        this.add(welcomeLabel);
        this.add(separator);
        this.add(Box.createRigidArea(new Dimension(0, 8)));
        this.add(overviewLabel);
        this.add(Box.createRigidArea(new Dimension(0, 12)));
        this.add(cardsContainer);
        this.add(separator2);
        this.add(payrollContainer);
        this.add(Box.createVerticalGlue()); // Absorbs remaining space at the bottom
    }
}