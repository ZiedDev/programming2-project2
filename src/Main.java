import UI.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // This is just to make ui elements match system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        new MainFrame();
    }
}
