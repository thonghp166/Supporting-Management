package com.test;

import com.Database.Database;
import com.Database.DatabaseControl;
import com.util.TypeEnum;
import com.util.util;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author dgbttn
 */

public class testDatabase {

    public static void createTable(Database DB, String month){
        ArrayList<String> columnName = new ArrayList<>();
        ArrayList<String> columnType = new ArrayList<>();

        String tableName = "2017#" + month +"#1#2";

        columnName.add("Nội dung"); columnType.add("Tự do");
        columnName.add("Kế hoạch"); columnType.add("Tự do");
        columnName.add("Thực tế"); columnType.add("Tự do");

        //DB.dropTable(tableName);
        DB.createTable(tableName, columnName, columnType);
    }

    public static void insertData(Database DB, String month){
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        ArrayList<String> row = new ArrayList<>();
        row = new ArrayList<>();
        row.add("Chi phí phương tiện vận tải");
        row.add("2.3");
        row.add("2.27");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí bảo trì, bảo dưỡng các hệ thống, đầu tư cơ sở hạ tầng của tòa nhà");
        row.add("1.5");
        row.add("1");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí sửa chữa thường xuyên, cải tạo, thay thế");
        row.add("3");
        row.add("2.2");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí nhiên liệu, điện, nước cung cấp cho tòa nhà");
        row.add("0.8");
        row.add("0.77");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí đảm bảo đời sống cho CBCNV tòa nhà");
        row.add("0.3");
        row.add("0.2");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí dịch vụ cảnh quan, an ninh tòa nhà");
        row.add("3");
        row.add("2.87");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí hành chính thường xuyên");
        row.add("2");
        row.add("2");
        data.add(row);
//        row = new ArrayList<>();
//        row.add("Chi phí bằng tiền khác (tiếp khách,…)");
//        row.add("1.6");
//        row.add("1.5");
//        data.add(row);
//        row = new ArrayList<>();
//        row.add("Chi phí bảng biển báo");
//        row.add("1");
//        row.add("1.1");
//        data.add(row);
//        row = new ArrayList<>();
//        row.add("Chi phí phát sinh");
//        row.add("0.5");
//        row.add("0.5");
//        data.add(row);

        DB.insertDataToTable("2017#" + month +"#1", data);
    }

    public static void updateData(Database DB, String month){
        String tableName = "2017#" + month +"#1";
        ArrayList<String> contents = DB.getStringOfColumn(tableName, "Nội dung");

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i=0; i<contents.size(); i++){
            String name = tableName + "#" + DB.getIndexOfContent("2017", contents.get(i));
            System.out.println(name);
            ArrayList<ArrayList<String>> subdata = DB.getDataOfTable(name);
            for (ArrayList<String> s : subdata) s.remove(0);
            ArrayList<String> sum = util.sum(subdata);
            data.add(sum);
        }

        DB.updateDataOfTable(tableName, data);
    }

    public static void main(String[] args) {
        Database DB = Database.getInstance();

        DB.ConnectToDatabase();

        for (int i=1; i<=1; i++) {
            updateData(DB, String.valueOf(i));
        }
        DB.Disconnect();


    }
}