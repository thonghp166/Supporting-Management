package template.defaultTemplate;

import basicComponents.DefaultFont;
import basicComponents.dynamicResizingTextField.DynamicResizingTextArea;
import template.content.Content;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.font.FontRenderContext;

/**
 * A panel including a label and a text area for a content
 *
 * @author Ha Tuan Phong
 */
public class ContentTextArea extends JPanel {
    /* content label(text area for wrapping) */
    JTextArea name;
    /* panel with flow layout for content label */
    JPanel panel_name;
    /* text area with auto resize available */
    /* IMPORTANT NOTE: resize function might not be use */
    DynamicResizingTextArea textArea = new DynamicResizingTextArea();
    /* If this content have sub-contents then no text area is added */
    boolean noTextArea = false;

    /**
     * Public constructor
     */
    public ContentTextArea() {
        super();
        this.setLayout(new BorderLayout());
    }

    /**
     * Set up label and text area with content parameter
     *
     * @param content the content to set
     */
    public void setContent(Content content) {
        this.name = new JTextArea();
        this.name.setFont(new Font("Arial", Font.PLAIN, 12));

        this.name.setEditable(false);
        this.name.setWrapStyleWord(true);
        this.name.setBackground(textArea.getBackground());
        this.name.setText(content.getContent());
        this.setOpaque(false);
        panel_name = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel_name.add(name);
        noTextArea = !content.isLeaf();
        setup();
    }

    /**
     * Clear text area(clear data function)
     */
    public void clearText() {
        if (noTextArea == true) return;
        textArea.clearText();
        System.out.println("clear");
    }

    /**
     * Get text
     *
     * @return text
     */
    public String getText() {
        if (noTextArea == true) return "";
        return this.textArea.getText();
    }

    /**
     * Add components to main panel
     */
    private void setup() {
        textArea.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(panel_name, BorderLayout.WEST);
        if (noTextArea == false)
            this.add(textArea, BorderLayout.CENTER);
    }

    /**
     * Resize textarea
     */
    public void resize() {
        if (noTextArea == false)
            textArea.resize();
        this.revalidate();
    }
}
