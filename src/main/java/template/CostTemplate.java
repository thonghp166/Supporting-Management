package template;

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
     * Import template's contents
     */
    /* Testing */
    @Override
    public void setContents() {
        for (int i = 0; i < DemoContent.contents.length; i++) {
            contents.add(new Content(DemoContent.contents[i]));
            //System.out.println(contents.get(i).getContent());
        }
    }

    /**
     * Get columns
     *
     * @return columns
     */
    public String[] getColumns() {
        return columns;
    }

    public ArrayList<Content> getContentList(){
        return contents;
    }
}

