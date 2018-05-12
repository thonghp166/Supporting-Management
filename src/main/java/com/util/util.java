package com.util;

import java.util.ArrayList;

/**
 * @author dgbttn
 */

public class util {
    public static double sum(ArrayList<String> seq){
        double sum = 0;
        for (String s : seq) sum+= Double.valueOf(s);
        return sum;
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
}