package template.template;

import org.pushingpixels.substance.internal.utils.SubstanceColorResource;

import javax.swing.*;
import java.util.ArrayList;

public abstract class TemplateUI extends JPanel {
    protected Template template;
    /**
     * Set template
     *
     * @param template the template to set
     */
    public void setTemplate(Template template) {
        this.template = template;
    }

    /**
     * get template
     *
     * @return template
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * Get data
     *
     * @return data
     */
    public abstract ArrayList<ArrayList<String>> getData();

    /**
     * Clear data
     */
    public abstract void clearData();
}
