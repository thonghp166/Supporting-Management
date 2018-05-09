package template;

import basicComponents.UIBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TemplatesAccessPanel extends JPanel {
    /* inner grid layout panel */
    private JPanel panel_inner;
    /* List of template-access buttons */
    private ArrayList<TemplateAccessButton> buttons_access;

    /**
     * Public constructor
     * Set default properties for panel and initialize list of buttons
     */
    public TemplatesAccessPanel() {
        super();
        this.setLayout(new FlowLayout());
        this.setMinimumSize(new Dimension(400, 400));
        this.setBorder(UIBorder.getInstance());
        panel_inner = new JPanel();
        panel_inner.setLayout(new GridLayout(0, 1, 3, 3));
        this.add(panel_inner);
        this.setVisible(true);
        buttons_access = new ArrayList<TemplateAccessButton>();
    }

    /**
     * Add new template access button
     *
     * @param template the template which the new button accesses to
     */
    public TemplateAccessButton addAccess(Template template) {
        TemplateAccessButton templateAccessButton = new TemplateAccessButton(template.getName());
        templateAccessButton.setKey(template.getKey());
        templateAccessButton.setBackground(Color.white);
        //update();
        return templateAccessButton;
    }

    /**
     * Remove specific template access from panel
     *
     * @param template the template whose access will be removed
     */
    public void removeAccess(Template template) {

    }

    /**
     * Update UI whenever there is a change in the list
     * Called by addAccess and removeAccess functions
     */
    private void update() {
        panel_inner.removeAll();
        for (JButton button : buttons_access) {
            System.out.println(button.getText());
            panel_inner.add(button);
        }
        panel_inner.updateUI();
    }

    /**
     * Check if there is no access
     *
     * @return true if there is no access, false otherwise
     */
    public boolean isEmpty() {
        return (buttons_access.isEmpty());
    }
}

