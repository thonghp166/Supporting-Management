package template.costTemplate;

import template.content.Content;
import template.template.Template;

import java.util.ArrayList;

/**
 * 'Biểu mẫu chi phí'
 * Always consists of 2 columns 'estimate'('Dự kiến') and 'actual'('Thực tế')
 *
 * @author Ha Tuan Phong
 */
public class CostTemplate extends Template {
    /* template's input columns */
    private final String[] columns = {"Dự kiến", "Thực tế"};

    /**
     * Public constructor
     */
    public CostTemplate() {
        super();
        name = "Chi phí";
    }

    /**
     * Public constructor
     *
     * @param name the name to set
     */
    public CostTemplate(String name) {
        this.name = name;
        contents = new ArrayList<Content>();
    }

    /**
     * Get columns
     *
     * @return columns
     */
    public String[] getColumns() {
        return columns;
    }
}

