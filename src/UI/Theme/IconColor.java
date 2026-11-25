package UI.Theme;

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

    public String getFolder() {
        return folder;
    }
}
