package core.sql;

import core.core.CoreMain;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLRanks {

    private final CoreMain plugin;

    public MySQLRanks(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PERMISSIONS " + "(NAME VARCHAR(100), UUID VARCHAR(100), PERMISSIONCODE INT(16), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM PERMISSIONS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPermissions(UUID uuid) {
        int permissionCode = 0;
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
