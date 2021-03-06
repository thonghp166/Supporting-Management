package com.util;

import java.util.Hashtable;

/**
 * @author dgbttn
 *
 * utils to transfer between kinds of type
 */

public class TypeUtils {
    // UI type to SQL type
    private static Hashtable<String, String> SQLTypes = new Hashtable<String, String>() {
        {
            put("Số", "INT(20)");
            put("Tự do", "VARCHAR(150)");
        }
    };

    // SQL type to chart type
    private static Hashtable<String, TypeEnum> chartTypes = new Hashtable<String, TypeEnum>() {
        {
            put("NUMBER", TypeEnum.NUMBER);
            put("STRING", TypeEnum.STRING);
        }
    };

    /**
     * transfer UI type to SQL type
     * @param type UI type
     * @return SQL type
     */
    public static String getSQLType(String type) {
        return SQLTypes.get(type);
    }

    /**
     * transfer SQL type to chart type
     * @param type SQL type
     * @return chart type
     */
    public static TypeEnum getChartType(String type) {
        return chartTypes.get(type);
    }

}