package template.defaultTemplate;


import basicComponents.UIBorder;
import basicComponents.UITitledBorder;
import basicComponents.dynamicResizingTextField.DynamicResizingTextArea;
import template.content.Content;
import template.template.Template;
import template.template.TemplateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * UI for default template
 *
 * @author Ha Tuan Phong
 */
public class DefaultTemplateUI extends TemplateUI {
    JPanel panel_inner = new JPanel();
    /* note: not necessary */
    Timer timer;
    /* list of content text areas */
    ArrayList<ContentTextArea> contentTextAreas;

    /**
     * Public constructor
     */
    public DefaultTemplateUI() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel_inner.setLayout(new GridLayout(0, 1, 0, 0));
        this.add(panel_inner);
    }

    /**
     * Set template
     *
     * @param template the template to set
     */
    @Override
    public void setTemplate(Template template) {
        this.template = template;
        this.setBorder(UITitledBorder.getTitleBorder(template.getName()));
        setup();
    }

    /**
     * Set up components
     */
    private void setup() {
        contentTextAreas = new ArrayList<ContentTextArea>();
        for (Content content : template.getContents()) {
            ArrayList<ContentTextArea> g_contentTextAreas = generateContentTextArea(content);
            for (ContentTextArea contentTextArea : g_contentTextAreas) {
                contentTextAreas.add(contentTextArea);
                panel_inner.add(contentTextArea);
            }
        }
        /*timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent().getParent().getParent();
                System.out.println(focused.getClass().toString());
                if (focused instanceof DynamicResizingTextArea) {
                }
            }
        });
        timer.start();*/
}

    /**
     * Content text area generator
     *
     * @param fatherContent the content to set
     * @return generated text area
     */
    private ArrayList<ContentTextArea> generateContentTextArea(Content fatherContent) {
        ArrayList<ContentTextArea> contentTextAreas = new ArrayList<ContentTextArea>();
        ContentTextArea contentTextArea = new ContentTextArea();
        contentTextArea.setContent(fatherContent);
        contentTextAreas.add(contentTextArea);
        if (!fatherContent.isLeaf()) {
            for (Content subContent : fatherContent.getSubContents()) {
                ArrayList<ContentTextArea> subContentTextAreas = generateContentTextArea(subContent);
                while (!subContentTextAreas.isEmpty()) {
                    contentTextAreas.add(subContentTextAreas.remove(0));
                }
            }
        }
        return contentTextAreas;
    }

    /**
     * Clear data
     */
    @Override
    public void clearData() {
        System.out.println(contentTextAreas.size());
        for (ContentTextArea contentTextArea : contentTextAreas) {
            contentTextArea.clearText();
        }
        panel_inner.updateUI();
    }

    /**
     * Get data
     *
     * @return data
     */
    @Override
    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for(ContentTextArea contentTextArea : contentTextAreas) {
            ArrayList<String> rowInput = new ArrayList<String>();
            rowInput.add(contentTextArea.getText());
            data.add(rowInput);
        }
        return data;
    }

}

