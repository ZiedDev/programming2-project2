package UI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public final class UITheme {
    private UITheme() {}

    // Fonts
    private static final String FONT_FAMILY = "Segoe UI";

    public static final Font FONT_TITLE    = new Font(FONT_FAMILY, Font.BOLD, 22);
    public static final Font FONT_DISPLAY  = new Font(FONT_FAMILY, Font.BOLD, 32);
    public static final Font FONT_BODY     = new Font(FONT_FAMILY, Font.PLAIN, 15);
    public static final Font FONT_SUBTITLE = new Font(FONT_FAMILY, Font.BOLD, 20);

    // Colors
    public static final Color BG_DARK      = new Color(38, 39, 46);
    public static final Color BG_BASE      = new Color(52, 55, 63);
    public static final Color BORDER       = new Color(82, 85, 93);
    public static final Color SURFACE_DARK = new Color(161, 165, 175);
    public static final Color SURFACE_BASE = new Color(197, 200, 208);
    public static final Color PRIMARY      = new Color(177, 238, 129);
    public static final Color PRIMARY_ALT  = new Color(187, 219, 169);
    public static final Color DANGER       = new Color(237, 131, 131);
    public static final Color DANGER_ALT   = new Color(186, 86, 86);
    public static final Color TEXT_DARK    = new Color(38, 39, 46);
    public static final Color TEXT_LIGHT   = new Color(197, 200, 208);

    // Icons
    public enum AppIcon {
        HOME("Home"),
        LOGIN("Login"),
        LOGOUT("Logout"),
        ADD("Add"),
        CANCEL("Cancel"),
        EDIT("Edit"),
        EMPLOYEE("Employee"),
        DEPARTMENT("Department"),
        MONEY("Money"),
        FULL_TIME("Full-Time"),
        PART_TIME("Part-Time");

        private final String file;

        AppIcon(String file) {
            this.file = file;
        }

        public ImageIcon get(IconColor color) {
            return new ImageIcon("src/resources/Icons/" + color.folder + "/" + file + ".png");
        }

        public ImageIcon get(IconColor color, int size) {
            Image img = get(color).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
    }

    public enum IconColor {
        DARK("Dark"),
        LIGHT("Light"),
        ACCENT("Accent"),
        ACCENT_RED("Accent_Red"),
        DANGER("Danger");

        private final String folder;

        IconColor(String folder) {
            this.folder = folder;
        }
    }
}