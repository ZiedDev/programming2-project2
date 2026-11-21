package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Popup {
    private JDialog dialog;

    public Popup(String titleText, JFrame parent) {
        dialog = new JDialog(parent, titleText, true);

        JPanel panel = new JPanel();
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(titleText);
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_LIGHT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);

        JPanel separator = ComponentFactory.createHorizontalSeparator();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(separator);

        panel.add(ComponentFactory.createInputField("Name", "", 20, null)); //TODO : remove later

        panel.add(new JLabel("POPUP"));
        JButton close = new JButton("close");
        close.addActionListener(e -> dialog.dispose());
        panel.add(close);

        dialog.add(panel);
        dialog.setLocationRelativeTo(parent);
        dialog.pack();
        return;
    }

    public void showPopup(JFrame parent) {
        dialog.setVisible(true);
    }
}
