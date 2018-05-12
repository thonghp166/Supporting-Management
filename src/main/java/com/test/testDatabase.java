package com.test;

import com.Database.Database;
import com.Database.DatabaseControl;
import com.util.TypeEnum;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author dgbttn
 */

public class testDatabase {

    public static void createTable(Database DB){
        LinkedList<String> columnName = new LinkedList<>();
        LinkedList<String> columnType = new LinkedList<>();

        String tableName = "2018#4#1#5";

        columnName.add("Nội dung"); columnType.add("Tự do");
        columnName.add("Kế hoạch"); columnType.add("Tự do");
        columnName.add("Thực tế"); columnType.add("Tự do");

        //DB.dropTable(tableName);
        DB.createTable(tableName, columnName, columnType);
    }

    public static void insertData(Database DB){
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        ArrayList<String> row = new ArrayList<>();
        row = new ArrayList<>();
        row.add("Chi phí điện văn phòng");
        row.add("2.3");
        row.add("2.27");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí tiền nước sinh hoạt");
        row.add("1.5");
        row.add("1");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí xăng, dầu chạy máy nổ");
        row.add("0.3");
        row.add("0.22");
        data.add(row);
        row = new ArrayList<>();
        row.add("Chi phí phát sinh\n");
        row.add("0");
        row.add("0");
        data.add(row);
//        row = new ArrayList<>();
//        row.add("Chi phí phát sinh");
//        row.add("0");
//        row.add("0");
//        data.add(row);
//        row = new ArrayList<>();
//        row.add("Chi phí dịch vụ cảnh quan, an ninh tòa nhà");
//        row.add("0");
//        row.add("0");
//        data.add(row);
//        row = new ArrayList<>();
//        row.add("Chi phí hành chính thường xuyên");
//        row.add("0");
//        row.add("0");
//        data.add(row);

        DB.insertDataToTable("2018#4#1#5", data);
    }

    public static void main(String[] args) {
        Database DB = Database.getInstance();

        DB.ConnectToDatabase();
        //DB.dropTable("2018#4#chi phí tòa nhà");
        //createTable(DB);
        //insertData(DB);
        //ArrayList<String> type = DB.getColumnNames("2018#chi phí tòa nhà");
        //for (String te : type) System.out.println(te);
        DB.Disconnect();

        int s = -1;
        try {
            s = Integer.valueOf("cont");
            System.out.println(s);
        }
        catch (Exception e){

        }
        System.out.println(s);

    }
}