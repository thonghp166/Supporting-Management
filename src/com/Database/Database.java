package com.Database;

import com.util.TypeEnum;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author dgbttn
 *
 * interface for user to connect and work with Database
 */

public interface Database {
    // The unique Database
    public static final Database instance = new DatabaseControl();

    // get the unique Database
    public static Database getInstance() {
        return instance;
    }

    /**
     * Connect to the Database
     */
    public void ConnectToDatabase();

    /**
     * Disconnect to the Database
     */
    public void Disconnect();

    /**
     * call a constant query to the database
     * @param Command the query
     */
    public void callConstantQuery(String Command);

    /**
     * create a table in the Datavbase
     * @param tableName name of the table
     * @param columnName list of columns' name of the table
     * @param columnType list of columns' type of the table
     */
    public void createTable(String tableName, ArrayList<String> columnName, ArrayList<String> columnType);

    /**
     * delete a table in Database with name
     * @param tableName name of the table
     */
    public void dropTable(String tableName);

    /**
     * clear all data of a table
     * @param tableName name of the table that need to be clear
     */
    public void clearTable(String tableName);

    /**
     * get name of table in database that corresponding to table's name of UI
     * @param tableName table's name of UI
     * @return table's name in database
     */
    //public String dataTableName(String tableName);

    /**
     * get number of rows of a table
     * @param tableName name of the table
     * @return number of rows
     */
    public int getRowCount(String tableName);

    /**
     * get the columns' type of a table under chart type
     * @param tableName name of the table
     * @return list of types
     */
    public ArrayList<TypeEnum> getColumnTypes(String tableName);

    /**
     * get the columns' name of a table
     * @param tableName name of the table
     * @return list of names
     */
    public ArrayList<String> getColumnNames(String tableName);

    /**
     * get all values in one column of a tbale
     * @param tableName name of the table
     * @param columnName name of the column
     * @return list of values
     */
    public ArrayList<Integer> getIntOfColumn(String tableName, String columnName);

    /**
     * get all values in one column of a tbale
     * @param tableName name of the table
     * @param columnName name of the column
     * @return list of values
     */
    public ArrayList<String> getStringOfColumn(String tableName, String columnName);

    /**
     * insert data to a table
     * @param tableName name of the table
     * @param data the data
     */
    public void insertDataToTable(String tableName, ArrayList<ArrayList<String>> data);

    /**
     * get data of a table
     * @param tableName name of the table
     * @return data
     */
    public ArrayList<ArrayList<String>> getDataOfTable(String tableName);

    /**
     * get all of contents
     * @return list of contents
     */
    public ArrayList<String> getListContents(String year);

    /**
     * get the parent-content of a sub-content
     * @param year year of contents
     * @param subContent sub-content
     * @return parent-content
     */
    public String getParentContent(String year, String subContent);

    /**
     * get index of a content under String type
     * @param year year of contents
     * @param content the content
     * @return its index
     */
    public String getIndexOfContent(String year, String content);

    /**
     * get data of table without its name
     * @param year year of data
     * @param month month of data
     * @param Subcontent the 2nd-level content
     * @return data
     */
    public ArrayList<ArrayList<String>> getData(String year, String month, String Subcontent);

    /**
     * update data to a table
     * @param tableName name of the table
     * @param data the data
     */
    public void updateDataOfTable(String tableName, ArrayList<ArrayList<String>> data);

    //public void updateAllData(int year, )
}