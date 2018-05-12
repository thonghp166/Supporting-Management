package template.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Parse contents from text to template
 *
 * @author Ha Tuan Phong
 */
public class ContentParser {
    /**
     * Parse function
     *
     * @param filePath
     * @return contents
     */
    public static ArrayList<Content> parseContents(String filePath) {
        ArrayList<Content> contents = new ArrayList<Content>();
        File file = new File(filePath);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (!str.isEmpty()) {
                    if (str.substring(0, 1).equals("\t")) {
                        str = str.substring(1,str.length());
                        contents.get(contents.size() - 1).addSubContent(new Content(str));
                    } else {
                        str = str.toUpperCase();
                        contents.add(new Content(str));
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contents;
    }

    /**
     * Test main
     * @param args
     */
    public static void main(String[] args) {
        parseContents("costcontents.txt");
    }
}
