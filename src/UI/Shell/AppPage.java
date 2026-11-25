package UI.Shell;

import UI.Components.Divider;
import UI.Theme.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class AppPage extends JPanel {
    protected final AppContext context;

    protected AppPage(AppContext context) {
        this.context = context;
        setBackground(UITheme.BG_DARK);
        setBorder(new EmptyBorder(
                UITheme.PAGE_PADDING,
                UITheme.PAGE_PADDING,
                UITheme.PAGE_PADDING,
                UITheme.PAGE_PADDING));
    }

    protected JPanel createHeader(String titleText) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(titleText);
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_LIGHT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        Divider separator = Divider.horizontal();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(separator);
        return header;
    }

    // Invoked by MainFrame everytime this page becomes active.
    public void onShow() {
        // Override to refresh data.
    }
}
