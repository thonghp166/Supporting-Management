package com.util;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * @author dgbttn
 */

public class util {
    public static ArrayList<String> sum(ArrayList<ArrayList<String>> seq){
        ArrayList<String> res = new ArrayList<>();

        double douRes[] = new double[seq.get(0).size()];
        for (int i=0; i<seq.get(0).size(); i++) douRes[i] = 0;

        for (ArrayList<String> s : seq) {
            for (int i=0; i<seq.get(0).size(); i++) {
                //System.out.println(Double.valueOf(s.get(i)));
                douRes[i] += Double.valueOf(s.get(i));
                System.out.println(douRes[i]);
            }
        }

        for (int i=0; i<seq.get(0).size(); i++) res.add(Double.toString(douRes[i]));
        return res;
    }

    public static ArrayList<ArrayList<String>> defaultData() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> subdata = new ArrayList<>();
        subdata.add("Trống");
        subdata.add("0");
        subdata.add("0");
        data.add(subdata);
        return data;
    }

    public static double stringToDouble(String string){
        String s[] = string.split("\\.");
        System.out.println(string);
        if (!string.contains(".")) return Double.valueOf(string);
        System.out.println(s[0]);
        System.out.println(s[1]);
        int a = Integer.valueOf(s[0]);
        int b = Integer.valueOf(s[1]);
        double result = (double) a + ((double) b) / Math.pow(10, s[1].length());
        return result;
    }
}