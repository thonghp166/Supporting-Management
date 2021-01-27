package basicComponents;

import javax.swing.border.TitledBorder;

/**
 * Titled border generator
 *
 * @author Ha Tuan Phong
 */
public class UITitledBorder {
    /**
     * Generate new titled border
     *
     * @param name the title to set
     * @return new titled border
     */
    public static TitledBorder getTitleBorder(String name) {
        TitledBorder titledBorder = new TitledBorder(UIBorder.getInstance(), name);
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        return titledBorder;
    }
}
