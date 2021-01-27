package template.template.templateGenerator;

import java.util.ArrayList;

public class Numbering {
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String dot = ".";

    public static String getNumber(int index) {
        if (index <= 0) return null;
        return alphabet.substring(index - 1, index) + ".";
    }

    public static String getNumber(ContentGenerator cg_root, ContentGenerator cg_toSet) {
        int layerToSet = 1;
        ArrayList<ContentGenerator> cgIterator = new ArrayList<ContentGenerator>();
        cgIterator.add(cg_root);
        mainLoop:
        while (!cgIterator.isEmpty()) {
            //System.out.println("loop");
            int times = cgIterator.size();
            for (int i = 0; i < times; i++) {
                if (cgIterator.get(0).equals(cg_toSet)) {
                    break mainLoop;
                }
                for (ContentGenerator subCg : cgIterator.get(0).getSubContentGenerators()) {
                    cgIterator.add(subCg);
                }
                cgIterator.remove(cgIterator.get(0));
            }
            layerToSet++;
        }
        System.out.println(layerToSet);
        System.out.println(cg_toSet.getParent().getContentString());
        if (layerToSet == 1) {
            return "Warning!";
        } else if (layerToSet == 2) {
            return IntegerToRomanConverter.toRoman(cg_toSet.getParent().getSubContentGenerators().indexOf(cg_toSet) + 1) + ".";
        } else if (layerToSet == 3) {
            return Integer.toString(cg_toSet.getParent().getSubContentGenerators().indexOf(cg_toSet) + 1) + ".";
        } else {
            return cg_toSet.getParent().getContentNumber() + Integer.toString(cg_toSet.getParent().getSubContentGenerators().indexOf(cg_toSet) + 1) + ".";
        }
    }
}
