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

    public static double stringToDouble(String string){
        String s[] = string.split("\\.");
        System.out.println(string);
        if (!string.contains(".")) return (double) Integer.valueOf(string);
        System.out.println(s[0]);
        System.out.println(s[1]);
        int a = Integer.valueOf(s[0]);
        int b = Integer.valueOf(s[1]);
        double result = (double) a + (double) ((double) b) / Math.pow(10, s[1].length());
        return result;
    }
}