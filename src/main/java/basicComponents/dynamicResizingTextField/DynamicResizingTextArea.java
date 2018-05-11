package basicComponents.dynamicResizingTextField;

import basicComponents.DefaultFont;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * JTextArea with auto resize ability
 *
 * @author Ha Tuan Phong
 */
public class DynamicResizingTextArea extends JPanel {
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private final int width = 300;
    private final int height = 30;
    private final int maxWidth = 400;
    private final int maxHeight = 120;

    /**
     * Public constructor
     */
    public DynamicResizingTextArea() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(DefaultFont.getInstance());
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(width, height));
        this.add(scrollPane);
    }

    /**
     * Resize text area based on text length in this component
     */
    public void resize() {
        String text = textArea.getText();
        if (text.length() * 10 > width) {
            if (scrollPane.getWidth() > maxWidth) {
                if (text.length() % 35 == 0) {

                    if (scrollPane.getHeight() < 80) {
                        System.out.println("WTF");
                        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), scrollPane.getHeight() + 40));
                    }
                }
            } else {
                scrollPane.setPreferredSize(new Dimension(text.length() * 10, scrollPane.getHeight()));
            }
        } else {
            scrollPane.setPreferredSize(new Dimension(width, height));
        }
        this.revalidate();
    }

    /**
     * Clear text for text area (clear data function)
     */
    public void clearText() {
        this.textArea.setText("");
        scrollPane.updateUI();
    }

    /**
     * Get text(data extraction)
     *
     * @return text
     */
    public String getText() {
        return this.textArea.getText();
    }
}
