package template.content;

import java.util.ArrayList;

/**
 * A content within a template, may consists of one/many sub contents
 * Ex: I/ Chi phí phương tiện vận tải
 * ---------1/ Chi phí xăng dầu, xe Văn phòng
 *
 * @author Hà Tuấn Phong
 */
public class Content {
    /* Content string */
    private String content;
    /* List of sub-contents, initialized null */
    private ArrayList<Content> subContents = new ArrayList<Content>();
    /* check if this content has no sub content */
    private boolean isLeaf = true;

    /**
     * Public constructor
     *
     * @param content content string to set
     */
    public Content(String content) {
        this.content = content;
    }

    /**
     * Content getter
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Content setter
     *
     * @param content content string to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Add new sub content to the list
     *
     * @param subContent the sub content to add
     */
    public void addSubContent(Content subContent) {
        if (subContents.isEmpty()) {
            this.isLeaf = false;
            this.subContents = new ArrayList<Content>();
        }
        this.subContents.add(subContent);
    }

    /**
     * Remove specific content form the list
     *
     * @param contentToRemove content string of the content to be removed
     * @return the removed content
     */
    public Content removeSubContent(String contentToRemove) {
        Content removed = null;
        for (Content c : subContents) {
            if (c.getContent().equals(contentToRemove)) {
                //removed = this.subContents.re;
            }
        }
        return removed;
    }

    /**
     * Get sub contents
     *
     * @return list of sub contents
     */
    public ArrayList<Content> getSubContents() {
        return subContents;
    }

    /**
     * Check if this content has no sub-contents
     *
     * @return isLeaf
     */
    public boolean isLeaf() {
        return isLeaf;
    }
}
