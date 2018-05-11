package template.template.templateGenerator;

import template.content.Content;

import javax.swing.*;
import java.awt.*;

public class ContentLabel extends JLabel {
    private int numberOfTabs = 0;

    public ContentLabel(ContentGenerator contentGenerator) {
        super(contentGenerator.getContentNumber() + contentGenerator.getContentString());
        ContentGenerator ancestor = contentGenerator.getParent();
        while (ancestor != null) {
            numberOfTabs++;
            ancestor = ancestor.getParent();
        }
    }

    public int getNumberOfTabs() {
        return numberOfTabs;
    }

    public static ContentLabel generateContentLabel(ContentGenerator contentGenerator) {
        return new ContentLabel(contentGenerator);
    }
}
