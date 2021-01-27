package template.template;

import javax.swing.*;

/**
 * Custom JButton with a key to identify template
 */
public class TemplateAccessButton extends JButton {
    /* identifying key */
    private int key;

    /**
     * Public constructor
     *
     * @param title the title to set(template's name)
     */
    public TemplateAccessButton(String title) {
        super(title);
    }

    /**
     * Set key
     *
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Get key
     *
     * @return key
     */
    public int getKey() {
        return key;
    }
}
