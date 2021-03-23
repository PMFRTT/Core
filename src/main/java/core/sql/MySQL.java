package core.sql;

import core.Utils;
import core.core.CoreSendStringPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private final String username = "root";
    private String password;

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            String host = "localhost";
            String port = "3306";
            String database = "pmfrtt_core_network";
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
            Bukkit.getLogger().info("Connection to database established");
            for(Player player : Bukkit.getOnlinePlayers()){
                CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&aDatenbank"), Utils.colorize("Verbindung hergestellt"));
            }
        }
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            connection.close();
            Bukkit.getLogger().info("Connection to database closed");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public MySQL() {

    }

}
