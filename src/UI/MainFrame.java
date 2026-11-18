package UI;
import UI.home.HomePage;
import UI.page1.PageOne;
import UI.page2.PageTwo;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String HOME = "HOME";
    public static final String PAGE_1 = "PAGE_1";
    public static final String PAGE_2 = "PAGE_2";

    private final CardLayout cardLayout;
    private final JPanel container;

    public MainFrame() {
        this.setTitle("App Title");
        this.setResizable(false);
        this.setSize(1024, 768 );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Adding sub-pages to the container
        container.add(new HomePage(this::switchPage), HOME);
        container.add(new PageOne(this::switchPage), PAGE_1);
        container.add(new PageTwo(this::switchPage), PAGE_2);

        this.setContentPane(container);
        this.setVisible(true);
    }

    public void switchPage(String pageName) {
        cardLayout.show(container, pageName);
    }
}
