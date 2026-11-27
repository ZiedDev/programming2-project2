package UI.Components;

import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.function.Consumer;

public class FormDialog<T> {
    private final FormBuilder<T> form;
    private String title = "";
    private String submitLabel = "Submit";
    private AppIcon submitIcon = AppIcon.ADD;
    private Consumer<T> onSubmit;

    public FormDialog(FormBuilder<T> form) {
        this.form = form;
    }

    public FormDialog<T> title(String title) {
        this.title = title;
        return this;
    }

    public FormDialog<T> submitLabel(String submitLabel) {
        this.submitLabel = submitLabel;
        return this;
    }

    public FormDialog<T> submitIcon(AppIcon submitIcon) {
        this.submitIcon = submitIcon;
        return this;
    }

    public FormDialog<T> onSubmit(Consumer<T> onSubmit) {
        this.onSubmit = onSubmit;
        return this;
    }

    public void show(Component anchor) {
        Window parent = anchor instanceof Window w ? w : SwingUtilities.getWindowAncestor(anchor);
        JDialog dialog = new JDialog(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(UITheme.BG_DARK);
        root.setBorder(new EmptyBorder(
                UITheme.PAGE_PADDING,
                UITheme.PAGE_PADDING,
                UITheme.PAGE_PADDING,
                UITheme.PAGE_PADDING));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(separator);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setOpaque(false);
        bodyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyPanel.add(form.getPanel());

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setOpaque(false);

        AppButton cancelBtn = AppButton.secondary("Cancel")
                .icon(AppIcon.CANCEL, IconColor.LIGHT)
                .onClick(dialog::dispose);

        AppButton submitBtn = AppButton.primary(submitLabel)
                .icon(submitIcon, IconColor.DARK)
                .onClick(() -> {
                    try {
                        T result = form.build();
                        if (onSubmit != null) {
                            onSubmit.accept(result);
                        }
                        dialog.dispose();
                    } catch (IllegalArgumentException ex) {
                        showError(dialog, "Invalid Input", ex.getMessage());
                    }
                });

        footerPanel.add(cancelBtn);
        footerPanel.add(submitBtn);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(bodyPanel, BorderLayout.CENTER);
        root.add(footerPanel, BorderLayout.SOUTH);

        dialog.setContentPane(root);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }

    public static void showWarning(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showError(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String title, String message) {
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }
}
