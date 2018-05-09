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

    public static void createTable(DatabaseControl DB){
        LinkedList<String> columnName = new LinkedList<>();
        LinkedList<String> columnType = new LinkedList<>();

        String tableName = "Biểu mẫu mới";

        columnName.add("Mã xe"); columnType.add("Tự do");
        columnName.add("Số km chạy"); columnType.add("Số");
        columnName.add("Ngày chạy"); columnType.add("Tự do");

        DB.dropTable(tableName);
        DB.createTable(tableName, columnName, columnType);
    }

    public static void insertData(DatabaseControl DB){
        try {
            Statement stmt = DB.getConnectToDatabase().createStatement();
            String Command = "INSERT INTO `biểu mẫu mới` (`Mã xe`, `Số km chạy`, `Ngày chạy`) VALUES " +
                    "('Xe máy', 100, '18/04/2018'), " +
                    "('Container', 150, '14/4/2018'), " +
                    "('Xe đạp', 34, '1/1/2018'), " +
                    "('Container', 150, '18/4/2018')," +
                    "('Xe đạp', 45, '18/4/2018');";
            stmt.executeUpdate(Command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseControl DB = (DatabaseControl) Database.getInstance();

        DB.ConnectToDatabase();
        //createTable(DB);
        //insertData(DB);
        ArrayList<TypeEnum> type = DB.getColumnTypes("Biểu mẫu mới");
        for (TypeEnum te : type) System.out.println(te);
        DB.Disconnect();

    }
}