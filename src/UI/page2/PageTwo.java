package UI.page2;

import UI.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PageTwo extends JPanel {
    public PageTwo(MainFrame frame) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Page 2", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton backBtn = new JButton("Back to Main");
        backBtn.addActionListener(e -> frame.switchPage(MainFrame.HOME));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.add(backBtn);

        add(title, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}