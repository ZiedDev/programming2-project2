package UI.Pages.Login;

import UI.Components.*;
import UI.Shell.AppContext;
import UI.Shell.AppPage;
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

public class LoginPage extends AppPage {
    private static final String ADMIN_HASH = "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9";

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
        setBorder(null);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_BASE);
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel titleLabel = new JLabel("Please Login", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleLabel.getPreferredSize().height));

        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        InputField usernameField = new InputField();
        usernameField.setPreferredSize(new Dimension(420, 36));
        LabeledField<InputField> usernameRow = LabeledField.vertical("Username", usernameField);
        usernameRow.setPreferredSize(new Dimension(420, usernameRow.getPreferredSize().height));

        PasswordField passwordField = new PasswordField();
        passwordField.setPreferredSize(new Dimension(420, 36));
        JPanel passwordRow = LabeledField.vertical("Password", passwordField);
        passwordRow.setPreferredSize(new Dimension(420, passwordRow.getPreferredSize().height));

        Divider separator2 = Divider.horizontal();
        separator2.setAlignmentX(Component.LEFT_ALIGNMENT);

        AppButton loginBtn = AppButton.primary("Login")
                .icon(AppIcon.LOGIN, IconColor.DARK)
                .alignLeft()
                .alignX(Component.LEFT_ALIGNMENT)
                .onClick(() -> {
                    String user = usernameField.getText().trim();
                    char[] pass = passwordField.getPassword();
                    try {
                        if ("admin".equals(user) && hash(new String(pass)).equals(ADMIN_HASH)) {
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
                .alignX(Component.LEFT_ALIGNMENT)
                .onClick(() -> System.exit(0));

        card.add(titleLabel);
        card.add(separator);
        card.add(usernameRow);
        card.add(passwordRow);
        card.add(separator2);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(exitBtn);

        add(card);
    }
}