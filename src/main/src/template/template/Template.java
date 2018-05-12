package template.template;

import template.content.Content;

import java.util.ArrayList;

public abstract class Template {
    /* key to compare with access panel */
    protected int key;
    /* template's name */
    protected String name;
    /* template's contents */
    protected ArrayList<Content> contents;

    /**
     * Public constructor
     */
    public Template() {
        contents = new ArrayList<Content>();
    }

    /**
     * set key
     *
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * get key
     *
     * @return key
     */
    public int getKey() {
        return key;
    }

    /**
     * set template's name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get template's name
     *
     * @return name;
     */
    public String getName() {
        return name;
    }

    /**
     * get contents
     *
     * @return contents
     */
    public ArrayList<Content> getContents() {
        return contents;
    }

    /**
     * set contents
     *
     * @param contents contents to set
     */
    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

    /**
     * Get number of sub contents without child of a specific content
     *
     * @param fatherContent specfific content to search
     * @return number of leafs
     */
    private int getNumberOfLeafContents(Content fatherContent) {
        int size = 0;
        for (Content content : fatherContent.getSubContents()) {
            if (content.isLeaf())
                size++;
            else size += getNumberOfLeafContents(content);
        }
        return size;
    }

    /**
     * Get number of sub contents without child in the contents list
     * @return number of leafs
     */
    public int getNumberOfLeafContents() {
        int size = 0;
        for(Content content: contents) {
            size += getNumberOfLeafContents(content);
        }
        return size;
    }
}