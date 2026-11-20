import UI.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // This is just to make ui elements match a universal look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        new MainFrame();
    }
}
