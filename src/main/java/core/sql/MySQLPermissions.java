package core.sql;

import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLPermissions {

    private final CoreMain plugin;

    public MySQLPermissions(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PERMISSIONS " + "(NAME VARCHAR(100), UUID VARCHAR(100), PERMISSIONCODE INT(16), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Permissions");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            String name = player.getName();
            UUID uuid = player.getUniqueId();
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM PERMISSIONS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!exists(uuid)) {
                PreparedStatement preparedStatement1 = CoreMain.SQL.getConnection().prepareStatement("INSERT IGNORE INTO PERMISSIONS (NAME,UUID) VALUES (?,?)");
                preparedStatement1.setString(1, name);
                preparedStatement1.setString(2, uuid.toString());
                preparedStatement1.executeUpdate();
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Permissions");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM PERMISSIONS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (exists)", "Permissions");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPermissions(UUID uuid, int permissionCode) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("UPDATE PERMISSIONS SET PERMISSIONCODE=? WHERE UUID=?");
            preparedStatement.setInt(1, permissionCode);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (set)", "Permissions");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPermissions(UUID uuid) {
        int permissionCode = 0;
        DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (get)", "Permissions");
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT PERMISSIONCODE FROM PERMISSIONS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                permissionCode = resultSet.getInt("PERMISSIONCODE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissionCode;
    }

}
