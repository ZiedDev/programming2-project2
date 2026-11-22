package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import javax.swing.SwingConstants;

public class Popup {
    private JDialog dialog;
    private JPanel panel;
    private JPanel bodyPanel;

    public Popup(String titleText, JFrame parent) {
        dialog = new JDialog(parent, titleText, true);

        panel = new JPanel();
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setLayout(new BorderLayout(12, 0));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_LIGHT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(title);

        JPanel separator = ComponentFactory.createHorizontalSeparator();
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(separator);

        // remove later

        // panel.add(new JLabel("POPUP"));
        // JButton close = new JButton("close");
        // close.addActionListener(e -> dialog.dispose());
        // panel.add(close);

        panel.add(headerPanel, BorderLayout.NORTH);
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setOpaque(false);
        panel.add(bodyPanel, BorderLayout.CENTER);

        dialog.add(panel);

        return;
    }

    public void showPopup(JFrame parent) {
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public void addComponent(Component c) {
        this.bodyPanel.add(c);
    }
}
