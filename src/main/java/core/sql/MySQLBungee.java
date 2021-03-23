package core.sql;

import core.core.CoreMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLBungee {

    private final CoreMain plugin;

    public MySQLBungee(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS SERVER " + "(NAME VARCHAR(100), PORT VARCHAR(100), PRIMARY KEY (NAME))");
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

    public String getServer(String name){
        String port = "";
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT PORT FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                port = resultSet.getString("PORT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return port;
    }

}
