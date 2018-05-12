package com.Database;

/**
 * @author dgbttn
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import com.util.*;


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
            stmt.close();
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
    public void clearTable(String tableName) {
        String clear = "TRUNCATE TABLE `" + tableName + "`";
        callConstantQuery(clear);
    }

    /**
    @Override
    public String dataTableName(String tableName) {
        ArrayList<String> names = getStringOfColumn("contents", "Nội dung");
        if (!names.contains(tableName)) {
            System.out.println("Table is not found!");
            return "";
        }
        return String.valueOf(names.indexOf(tableName)+1);
    }*/

    @Override
    public int getRowCount(String tableName) {
        int rowCount = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM `" + tableName + "`");
            rs.next();
            rowCount =  rs.getInt(1);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
        return rowCount;
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
            rs.close();
            stmt.close();
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
            rs.close();
            stmt.close();
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
    public ArrayList<String> getListContents(int year) {
        String tableName = String.valueOf(year) + "#" + "contents";
        //System.out.println(tableName);
        return getStringOfColumn(tableName, "Nội dung");
    }

    @Override
    public String getParentContent(int year, String subContent) {
        ArrayList<String> contents = getListContents(year);
        subContent = subContent.trim();
        int index = contents.indexOf(subContent);
        while (index>=0) {
            String name = contents.get(index);
            if (name.compareTo(name.toUpperCase())==0) break;
            index--;
        }
        if (index<0) {
            System.out.println("Sub-content is invalid!");
            return "None";
        }
        return contents.get(index);
    }

    @Override
    public String getIndexOfContent(int year, String content) {
        ArrayList<String> contents = getListContents(year);
        content = content.trim();
        return String.valueOf(contents.indexOf(content)+1);
    }

    @Override
    public ArrayList<ArrayList<String>> getData(int year, int month, String Subcontent) {
        if (year == 0){
            System.out.println("List of Contents has an mistake!");
            System.out.println("Or the data is empty!");
            return util.defaultData();
        }
        Subcontent = Subcontent.trim();
        String parentContent = getParentContent(year, Subcontent);
        if (parentContent.equals("None")) return util.defaultData();

        String tableName = String.valueOf(year)+ "#" + String.valueOf(month) + "#" + getIndexOfContent(year, parentContent);
        if (!Subcontent.equals(Subcontent.toUpperCase())) tableName+= "#" + getIndexOfContent(year, Subcontent);
        //System.out.println(tableName);
        //return null;

        return getDataOfTable(tableName);
    }

    @Override
    public void updateDataOfTable(String tableName, ArrayList<ArrayList<String>> data) {
        if (getRowCount(tableName) != data.size()) {
            System.out.println("Data is not compatible!");
            return;
        }
        clearTable(tableName);
        insertDataToTable(tableName, data);
    }

    @Override
    public ArrayList<String> getStringOfColumn(String tableName, String columnName) {
        ArrayList<String> valueList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `" + tableName + "` ;");
            while (rs.next()) valueList.add(rs.getString(columnName));
            rs.close();
            stmt.close();
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
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Create Statement Failed!");
            e.printStackTrace();
        }
        return valueList;
    }

}