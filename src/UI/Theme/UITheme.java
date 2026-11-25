package UI.Theme;

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

    // Layout & Sizing
    public static final int PAGE_PADDING         = 24;
    public static final int ICON_SIZE            = 24;
    public static final int BUTTON_HEIGHT        = 64;
    public static final int FORM_CONTROL_COLUMNS = 20;
    public static final int TABLE_ROW_HEIGHT     = 28;
}