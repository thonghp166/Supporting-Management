package template.template.templateGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContentGenerator {
    private String contentNumber;
    private String contentString;
    private ArrayList<ContentGenerator> subContentGenerators = new ArrayList<ContentGenerator>();
    private ContentGenerator parent = null;

    public ContentGenerator(String contentString) {
        this.contentString = contentString;
    }

    public void setContentNumber(String contentNumber) {
        this.contentNumber = contentNumber;
    }

    public String getContentNumber() {
        return contentNumber;
    }

    public String getContentString() {
        return contentString;
    }
    public ArrayList<ContentGenerator> getSubContentGenerators() {
        return subContentGenerators;
    }

    public void addSubContentGenerator(ContentGenerator subCg) {
        subContentGenerators.add(subCg);
        subCg.setParent(this);
    }

    public ContentGenerator getParent() {
        return parent;
    }

    public void setParent(ContentGenerator parent) {
        this.parent = parent;
    }

}
