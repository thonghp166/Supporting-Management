package basicComponents;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Font for UI
 * @author Ha Tuan Phong
 */
public class DefaultFont extends Font {
    private static final DefaultFont instance = new DefaultFont();

    /**
     * Private constructor
     */
    private DefaultFont() {
        super("", Font.PLAIN, 12);
    }

    /**
     * get instance
     * @return instance
     */
    public static DefaultFont getInstance() {
        return instance;
    }
}
