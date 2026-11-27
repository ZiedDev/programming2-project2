package UI.Theme;

import javax.swing.*;
import java.awt.*;

// Icons
public enum AppIcon {
    HOME("Home"),
    LOGIN("Login"),
    LOGOUT("Logout"),
    ADD("Add"),
    CANCEL("Cancel"),
    TICK("Tick"),
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
        return new ImageIcon("src/resources/Icons/" + color.getFolder() + "/" + file + ".png");
    }

    public ImageIcon get(IconColor color, int size) {
        Image img = get(color).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
