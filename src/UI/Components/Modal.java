package UI.Components;

import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class Modal {
    private Modal() {
    }

    // Form modals
    public static <T> void show(
            Component anchor,
            String title,
            String submitLabel,
            FormBuilder<T> form,
            Consumer<T> onSubmit) {
        show(anchor, title, submitLabel, AppIcon.ADD, form, onSubmit);
    }

    public static <T> void show(
            Component anchor,
            String title,
            String submitLabel,
            AppIcon submitIcon,
            FormBuilder<T> form,
            Consumer<T> onSubmit) {
        JDialog dialog = createDialog(anchor, title, UITheme.TEXT_LIGHT, form);

        AppButton cancelBtn = AppButton.secondaryAlt("Cancel")
                .icon(AppIcon.CANCEL, IconColor.LIGHT)
                .onClick(dialog::dispose);

        AppButton submitBtn = AppButton.primary(submitLabel)
                .icon(submitIcon, IconColor.ACCENT)
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

        showWithFooter(dialog, submitBtn, cancelBtn, submitBtn);
    }

    // Alert & confirm dialogs
    public static void showInfo(Component anchor, String message) {
        showMessage(anchor, "Info", message, UITheme.TEXT_LIGHT, AppButton.primary("OK").icon(AppIcon.TICK));
    }

    public static void showWarning(Component anchor, String title, String message) {
        showMessage(anchor, title, message, UITheme.PRIMARY, AppButton.primary("OK").icon(AppIcon.TICK));
    }

    public static void showError(Component anchor, String title, String message) {
        showMessage(anchor, title, message, UITheme.DANGER, AppButton.danger("OK").icon(AppIcon.TICK));
    }

    public static boolean confirm(Component anchor, String title, String message) {
        // AtomicBoolean lets the Yes-button lambda mutate the result
        AtomicBoolean confirmed = new AtomicBoolean(false);
        JDialog dialog = createDialog(anchor, title, UITheme.TEXT_LIGHT, createMessageLabel(message));

        AppButton noBtn = AppButton.secondaryAlt("No")
                .icon(AppIcon.CANCEL, IconColor.LIGHT)
                .onClick(dialog::dispose);

        AppButton yesBtn = AppButton.danger("Yes")
                .icon(AppIcon.TICK)
                .onClick(() -> {
                    confirmed.set(true);
                    dialog.dispose();
                });

        showWithFooter(dialog, noBtn, noBtn, yesBtn);
        return confirmed.get();
    }

    // Dialog assembly helpers
    private static void showMessage(Component anchor, String title, String message, Color titleColor, AppButton okBtn) {
        JDialog dialog = createDialog(anchor, title, titleColor, createMessageLabel(message));
        okBtn.onClick(dialog::dispose);
        showWithFooter(dialog, okBtn, okBtn);
    }

    private static JLabel createMessageLabel(String message) {
        JLabel label = new JLabel(message != null ? message : "");
        label.setFont(UITheme.FONT_BODY);
        label.setForeground(UITheme.TEXT_LIGHT);
        return label;
    }

    private static JDialog createDialog(Component anchor, String title, Color titleColor, Component bodyContent) {
        Window parent = anchor instanceof Window w ? w : SwingUtilities.getWindowAncestor(anchor);
        JDialog dialog = new JDialog(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

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
        titleLabel.setForeground(titleColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        Divider headerDivider = Divider.horizontal();
        headerDivider.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(headerDivider);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(bodyContent, BorderLayout.CENTER);
        dialog.setContentPane(root);
        return dialog;
    }

    private static void showWithFooter(JDialog dialog, AppButton defaultBtn, AppButton... footerButtons) {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setOpaque(false);
        footerPanel.add(Divider.horizontal());

        JPanel buttonsRow = new JPanel(new GridLayout(1, footerButtons.length, 10, 0));
        buttonsRow.setOpaque(false);
        buttonsRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (AppButton btn : footerButtons) {
            btn.alignLeft();
            buttonsRow.add(btn);
        }
        footerPanel.add(buttonsRow);

        dialog.getContentPane().add(footerPanel, BorderLayout.SOUTH);
        dialog.getRootPane().setDefaultButton(defaultBtn);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.setVisible(true); // Blocks here until dialog.dispose() runs
    }
}
