package core.sql;

import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;

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
            preparedStatement = CoreMain.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS CONFIG " + "(NAME VARCHAR(100), VALUE VARCHAR(100), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean exists(String string) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, string);
            ResultSet resultSet = preparedStatement.executeQuery();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getConfigbyName(String name){
        String value = "";
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT VALUE FROM CONFIG WHERE NAME=?");
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
