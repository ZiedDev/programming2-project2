package UI.Pages.Login;

import UI.Components.AppButton;
import UI.Components.ComponentFactory;
import UI.Components.Divider;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
import UI.Shell.MainFrame;
import UI.Shell.Route;
import UI.Theme.UITheme;
import UI.Theme.AppIcon;
import UI.Theme.IconColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.function.Consumer;

public class LoginPage extends AppPage {
    private static String adminHash = "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9";

    public static String hash(String input) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginPage(AppContext context) {
        super(context);
        this.setLayout(new GridBagLayout());
        this.setBackground(UITheme.BG_DARK);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_BASE);
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel titleLabel = new JLabel("Please Login");
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(UITheme.FONT_BODY);
        userLabel.setForeground(UITheme.TEXT_LIGHT);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(320, 36));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(UITheme.FONT_BODY);
        passLabel.setForeground(UITheme.TEXT_LIGHT);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(320, 36));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        AppButton loginBtn = AppButton.primary("Login")
                .icon(AppIcon.LOGIN, IconColor.DARK)
                .alignLeft()
                .onClick(() -> {
                    String user = usernameField.getText().trim();
                    char[] pass = passwordField.getPassword();
                    try {
                        if ("admin".equals(user) && hash(new String(pass)).equals(adminHash)) {
                            passwordField.setText("");
                            context.navigate(Route.HOME);
                        }
                    } finally {
                        Arrays.fill(pass, '\0');
                    }
                });

        AppButton exitBtn = AppButton.danger("Exit")
                .icon(AppIcon.CANCEL, IconColor.DANGER)
                .alignLeft()
                .onClick(()-> System.exit(0));

        exitBtn.addActionListener(e -> System.exit(0));

        card.add(titleLabel);
        card.add(separator);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(userLabel);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(usernameField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(passLabel);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(passwordField);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(exitBtn);

        this.add(card);
    }
}