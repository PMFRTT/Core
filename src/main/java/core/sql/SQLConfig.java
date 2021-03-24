package core.sql;

import core.core.CoreMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConfig {

    private final CoreMain plugin;

    public SQLConfig(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS CONFIG " + "(NAME VARCHAR(100), VALUE VARCHAR(100), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addServer(String name, String port) {
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!exists(name)) {
                PreparedStatement preparedStatement1 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO SERVER (NAME,PORT) VALUES (?,?)");
                preparedStatement1.setString(1, name);
                preparedStatement1.setString(2, port);
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String string) {
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, string);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getConfigbyName(String name){
        String value = "";
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT VALUE FROM CONFIG WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                value = resultSet.getString("VALUE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }


}
