package template;

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
     */
    public abstract void setContents();
}