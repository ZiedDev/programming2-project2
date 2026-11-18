package UI.home;

import UI.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class HomePage extends JPanel {
    public HomePage(Consumer<String> navigate) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Main Page", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        JButton page1Btn = new JButton("Open Page 1");  
        JButton page2Btn = new JButton("Open Page 2");

        page1Btn.addActionListener(e -> navigate.accept(MainFrame.PAGE_1));
        page2Btn.addActionListener(e -> navigate.accept(MainFrame.PAGE_2));

        buttonRow.add(page1Btn);
        buttonRow.add(page2Btn);

        add(title, BorderLayout.CENTER);
        add(buttonRow, BorderLayout.SOUTH);
    }
}