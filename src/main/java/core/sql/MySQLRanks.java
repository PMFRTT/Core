package core.sql;

import core.core.CoreHandler;
import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLRanks {

    private final CoreMain plugin = CoreHandler.getMain();


    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreHandler.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS RANKS " + "(NAME VARCHAR(100), UUID VARCHAR(100), RANK INT(16), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Ranks");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            String name = player.getName();
            UUID uuid = player.getUniqueId();
            PreparedStatement preparedStatement = CoreHandler.getSQL().getConnection().prepareStatement("SELECT * FROM RANKS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!exists(uuid)) {
                PreparedStatement preparedStatement1 = CoreHandler.getSQL().getConnection().prepareStatement("INSERT IGNORE INTO RANKS (NAME,UUID) VALUES (?,?)");
                preparedStatement1.setString(1, name);
                preparedStatement1.setString(2, uuid.toString());
                preparedStatement1.executeUpdate();
                setRanks(uuid, 1);
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Ranks");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement preparedStatement = CoreHandler.getSQL().getConnection().prepareStatement("SELECT * FROM RANKS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (exists)", "Ranks");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setRanks(UUID uuid, int rank) {
        try {
            PreparedStatement preparedStatement = CoreHandler.getSQL().getConnection().prepareStatement("UPDATE RANKS SET RANK=? WHERE UUID=?");
            preparedStatement.setInt(1, rank);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (set)", "Ranks");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRank(UUID uuid) {
        int permissionCode = 0;
        try {
            PreparedStatement preparedStatement = CoreHandler.getSQL().getConnection().prepareStatement("SELECT RANK FROM RANKS WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                permissionCode = resultSet.getInt("RANK");
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (get)", "Ranks");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissionCode;
    }

}
