package com.Database;

/**
 * @author dgbttn
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.util.TypeEnum;
import com.util.TypeUtils;
import javafx.util.Pair;


public class DatabaseControl implements Database {
    private static String DB_URL = "jdbc:mysql://localhost:3306/testjdbc";
    private static String USER_NAME = "root";
    private static String PASSWORD = "0918273645";

    private static Connection connection = null;

    DatabaseControl() {}

    /**
     * get the connection to the DB
     * @return the connection
     */
    public Connection getConnectToDatabase() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("Connect successfully!");
        } catch (Exception ex) {
            System.out.println("Connect failed!");
            ex.printStackTrace();
        }
        return conn;
    }

    @Override
    public void ConnectToDatabase() {
        connection = getConnectToDatabase();
    }

    @Override
    public void Disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callConstantQuery(String Command) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(Command);
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String tableName, LinkedList<String> columnName, LinkedList<String> columnType)  {
        if (columnName.size()!=columnType.size()) {
            System.out.println("Table " + tableName + "is invalid!");
            return;
        }
        if (columnName.size()*columnType.size() == 0) {
            System.out.println("Table " + tableName + "is empty!");
            return;
        }

        String name, type;
        String Command = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( ";
        Command += "\n `STT` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY ";
        for (int i=0; i<columnName.size(); i++) {
            name = columnName.get(i);
            type = TypeUtils.getSQLType(columnType.get(i));
            if (type==null) {
                System.out.println("Type " + type + "is invalid");
                return;
            }

            Command += ", \n `" + name + "` " + type + " NOT NULL ";
            Command += (type.contains("VARCHAR"))? "COLLATE utf8_vietnamese_ci": "";
        }
        Command += ") \n ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci; ";

        //System.out.println(Command);

        callConstantQuery(Command);
        System.out.println("Create Table " + tableName + " Successfully!");
    }

    @Override
    public void dropTable(String tableName) {
        callConstantQuery("DROP TABLE IF EXISTS`" + tableName + "` ;");
        System.out.println("Remove Table " + tableName + " Successfully!");
    }

    @Override
    public ArrayList<TypeEnum> getColumnTypes(String tableName) {
        ArrayList<TypeEnum> columnTypes = new ArrayList<>();
        String selectAll = "SELECT * FROM `"+tableName +"` ;";

        try {
            PreparedStatement stmt = connection.prepareStatement(selectAll);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i=2; i<=rsmd.getColumnCount(); i++)
                columnTypes.add(TypeUtils.getChartType(rsmd.getColumnTypeName(i)));
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
        return columnTypes;
    }

    @Override
    public ArrayList<String> getColumnNames(String tableName){
        ArrayList<String> columnNames = new ArrayList<>();
        String selectAll = "SELECT * FROM `"+tableName +"` ;";

        try {
            PreparedStatement stmt = connection.prepareStatement(selectAll);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i=2; i<=rsmd.getColumnCount(); i++)
                columnNames.add(rsmd.getColumnName(i));
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
        return columnNames;
    }

    @Override
    public void insertDataToTable(String tableName, ArrayList<ArrayList<String>> data) {
        ArrayList<String> colNames = getColumnNames(tableName);
        if (data.get(0).size() != colNames.size()) {
            System.out.println("Data to table "+ tableName + " is invalid!");
            System.out.println("Insert Data Failed!");
            return;
        }

        String Command = "INSERT INTO `" + tableName + "` \n(";
        for (String name : colNames) Command += "`" + name + "`,";
        Command = Command.substring(0, Command.length()-1);
        Command += ") \n VALUES ";

        for (ArrayList<String> row : data) {
            String rowString = "\n(";
            for (String value : row)
                rowString += "\'"+value+"\',";
            rowString = rowString.substring(0, rowString.length()-1);
            Command += rowString + "),";
        }
        Command = Command.substring(0,Command.length()-1);
        //System.out.println(Command);

        callConstantQuery(Command);
        System.out.println("Insert Data Successfully!");
    }

    @Override
    public ArrayList<ArrayList<String>> getDataOfTable(String tableName) {
        ArrayList<String> colNames = getColumnNames(tableName);
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (String name : colNames) {
            ArrayList<String> columnData = getStringOfColumn(tableName, name);
            data.add(columnData);
        }

        return data;
    }

    @Override
    public ArrayList<String> getListContents() {
        ArrayList<String> list = new ArrayList<>();
        list.add("CHI PHÍ TÒA NHÀ");
        list.add("Chi phí phương tiện vt");
        list.add("Tiền nhà");
        list.add("Tiền điện");
        list.add("Tiền nước");
        list.add("CHI PHÍ DI CHUYỂN");
        list.add("Tiền vé xe");
        list.add("Tiền sửa xe");
        return list;

        //return getStringOfColumn("contents", "Nội dung");
    }

    @Override
    public ArrayList<ArrayList<String>> getData(int year, int month, String Subcontent) {
        ArrayList<String> contents = getListContents();
        Subcontent = Subcontent.trim();
        int index = contents.indexOf(Subcontent);
        while (index>=0) {
            String name = contents.get(index);
            if (name.compareTo(name.toUpperCase())==0) break;
            index--;
        }
        if (index<0){
            System.out.println("List of Contents has an mistake!");
            ArrayList<ArrayList<String>> data = new ArrayList<>();
            ArrayList<String> subdata = new ArrayList<>();
            ArrayList<String> subdata1 = new ArrayList<>();
            ArrayList<String> subdata2 = new ArrayList<>();
            subdata.add("CHI PHÍ TÒA NHÀ");
            subdata.add("Chi phí vật liệu");
            subdata.add("Chi phí phát sinh");
            subdata.add("Chi phí xe");
            subdata.add("CHI PHÍ TIÊU THỤ");
            subdata.add("Chi phí điện");
            subdata.add("Chi phí nước");
            subdata.add("Chi phí VPP");
            data.add(subdata);
            subdata1.add("100");
            subdata1.add("30");
            subdata1.add("50");
            subdata1.add("20");
            subdata1.add("146");
            subdata1.add("40");
            subdata1.add("56");
            subdata1.add("50");
            data.add(subdata1);
            subdata2.add("300");
            subdata2.add("130");
            subdata2.add("150");
            subdata2.add("20");
            subdata2.add("200");
            subdata2.add("50");
            subdata2.add("100");
            subdata2.add("50");
            data.add(subdata2);
            return data;
        }
        String tableName = String.valueOf(year)+ "#" + String.valueOf(month) + "#" + contents.get(index);
        if (!Subcontent.equals(Subcontent.toUpperCase())) tableName+= "#" + Subcontent;
        System.out.println(tableName);
        return null;
        //return getDataOfTable(tableName);
    }

    @Override
    public ArrayList<String> getStringOfColumn(String tableName, String columnName) {
        ArrayList<String> valueList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `" + tableName + "` ;");
            while (rs.next()) valueList.add(rs.getString(columnName));
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
        return valueList;
    }

    @Override
    public ArrayList<Integer> getIntOfColumn(String tableName, String columnName) {
        ArrayList<Integer> valueList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `" + tableName + "` ;");
            while (rs.next()) valueList.add(rs.getInt(columnName));
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
        return valueList;
    }

}