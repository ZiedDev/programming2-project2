package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

import UI.UITheme.AppIcon;
import UI.UITheme.IconColor;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.util.function.Consumer;

import UI.Forms.FormBuilder;

public class Popup {
    private JDialog dialog;
    private JPanel panel;
    private JPanel bodyPanel;
    private JPanel footerPanel;

    public Popup(String titleText, JFrame parent) {
        dialog = new JDialog(parent, titleText, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setLayout(new BorderLayout(0, 12));

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
        bodyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(bodyPanel, BorderLayout.CENTER);

        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setOpaque(false);
        footerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(footerPanel, BorderLayout.SOUTH);

        dialog.add(panel);

        return;
    }

    public void showPopup(JFrame parent) {
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public void repack() {
        dialog.pack();
    }

    public void close() {
        dialog.dispose();
    }

    public void addBodyComponent(Component c) {
        this.bodyPanel.add(c);
    }

    public void addFooterComponent(Component c) {
        this.footerPanel.add(c);
    }

    public static <T> void showForm(
            JFrame parent,
            String title,
            String submitLabel,
            FormBuilder<T> form,
            Consumer<T> onSubmit) {
        Popup popup = new Popup(title, parent);
        popup.addBodyComponent(form.getPanel());

        JButton cancelBtn = ComponentFactory.createSecondaryButton("Cancel", AppIcon.CANCEL, IconColor.LIGHT);
        cancelBtn.addActionListener(e -> popup.close());

        JButton submitBtn = ComponentFactory.createPrimaryButton(submitLabel, AppIcon.ADD, IconColor.DARK);
        submitBtn.addActionListener(e -> {
            try {
                T result = form.build();
                onSubmit.accept(result);
                popup.close();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        popup.dialog, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        popup.addFooterComponent(cancelBtn);
        popup.addFooterComponent(submitBtn);
        popup.showPopup(parent);
    }
}
