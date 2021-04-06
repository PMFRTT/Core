package core.sql;

import core.bungee.Server;
import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLBungee {

    private final CoreMain plugin;

    public MySQLBungee(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS SERVER " + "(NAME VARCHAR(100), PORT INT(5), VERSION VARCHAR(16), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Bungee");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addServer(String name, String port) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!exists(name)) {
                PreparedStatement preparedStatement1 = CoreMain.SQL.getConnection().prepareStatement("INSERT IGNORE INTO SERVER (NAME,PORT) VALUES (?,?)");
                preparedStatement1.setString(1, name);
                preparedStatement1.setString(2, port);
                preparedStatement1.executeUpdate();
                DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (set)", "Bungee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String string) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, string);
            ResultSet resultSet = preparedStatement.executeQuery();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (exists)", "Bungee");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Server> getServers(){
        ArrayList<Server> servers = new ArrayList<Server>();
        try{
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM SERVER");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                servers.add(new Server(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3)));
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (get)", "Bungee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servers;
    }

    public Integer getPort(String name){
        int port = 0;
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT PORT FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                port = resultSet.getInt("PORT");
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (get)", "Bungee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return port;
    }

    public String getVersion(String name){
        String version = "";
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT VERSION FROM SERVER WHERE NAME=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                version = resultSet.getString("VERSION");
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (get)", "Bungee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return version;
    }

}
