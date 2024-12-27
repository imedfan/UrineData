package com.urinetank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UrinaDAO {

    public final static String URL = "jdbc:sqlite:urineData.db";
    private Connection connection;

    UrinaDAO(Connection connection){
        this.connection = connection;
    }

    void createDB(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS urineData (id INTEGER PRIMARY KEY, value TEXT, datatime DATETIME)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void execute(String command){
        try (Connection connection = DriverManager.getConnection(URL) ) {
            Statement statement = connection.createStatement();
            statement.execute(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }

    void saveUrineData(String value){
        String sql = "INSERT INTO urineData (value, datatime) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL) ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            preparedStatement.setString(1, value);
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }

    ResultSet get(){
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM urineData");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }
}
