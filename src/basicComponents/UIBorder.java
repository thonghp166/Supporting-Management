package basicComponents;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Defualt border for UI
 *
 * @author Ha Tuan Phong
 */
public final class UIBorder {
    private static Border instance = BorderFactory.createLineBorder(Color.GRAY);

    /**
     * Private constructor
     * Ignore
     */
    private UIBorder() {
    }

    /**
     * Get instance
     *
     * @return instance
     */
    public static Border getInstance() {
        return instance;
    }
}
