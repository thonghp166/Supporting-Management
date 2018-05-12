package basicComponents;

import javax.swing.*;

/**
 * Menubar for UI
 * Using singleton pattern for 1 instance only purpose
 *
 * @author Ha Tuan Phong
 */
public class MenuBar extends JMenuBar {
    /* menubar instance */
    private static MenuBar instance = new MenuBar();
    /* template menu */
    private JMenu menu_template;

    /**
     * Private constructor
     */
    private MenuBar() {
        super();
        setup();
    }

    /**
     * get instance
     *
     * @return instance
     */
    public static MenuBar getInstance() {
        return instance;
    }

    /**
     * set up components
     */
    private void setup() {
        menu_template = new JMenu();
        menu_template.setText("Quản lý biểu mẫu");
        this.add(menu_template);
    }
}
