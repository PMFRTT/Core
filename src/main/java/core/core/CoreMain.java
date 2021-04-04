package core.core;


import core.TPS;
import core.Utils;
import core.bungee.CoreBungeeCordClient;
import core.commands.MainCommandListener;
import core.debug.DebugSender;
import core.debug.DebugType;
import core.permissions.PermissionConverter;
import core.sql.*;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class CoreMain extends JavaPlugin {

    public static MySQL SQL;

    public static MySQLPermissions mySQLPermissions;
    public static MySQLBungee mySQLBungee;
    public static SQLConfig sqlConfig;
    public static MySQLRanks mySQLRanks;

    private TPS ticker;

    public PermissionConverter permissionConverter;

    private static String serverName = "loading";
    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        CoreMain.plugin = plugin;
        serverName = plugin.getName();
    }

    public void onEnable() {

        this.ticker = new TPS(this);

        SQL = new MySQL();
        mySQLPermissions = new MySQLPermissions(this);
        mySQLBungee = new MySQLBungee(this);
        sqlConfig = new SQLConfig(this);
        mySQLRanks = new MySQLRanks(this);

        this.permissionConverter = new PermissionConverter(this);

        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database is not connected");
        }

        if (SQL.isConnected()) {
            mySQLPermissions.createTable();
            mySQLBungee.createTable();
            sqlConfig.createTable();
            mySQLRanks.createTable();
            for (Player player : Bukkit.getOnlinePlayers()) {
                mySQLPermissions.createPlayer(player);
                mySQLRanks.createPlayer(player);
                if (mySQLPermissions.getPermissions(player.getUniqueId()) == 0) {
                    mySQLPermissions.setPermissions(player.getUniqueId(), 0);
                }
                PermissionConverter.generatePermissions(PermissionConverter.convertIntToBinary(mySQLPermissions.getPermissions(player.getUniqueId())));

            }
        }


        CoreBungeeCordClient bungeeCordClient = new CoreBungeeCordClient(this);
        CoreEventHandler coreEventHandler = new CoreEventHandler(this);
        CoreResetServer coreResetServer = new CoreResetServer(this);
        Utils utils = new Utils(this);

        Utils.changeGamerule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        MainCommandListener mainCommandListener = new MainCommandListener(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Utils.addServerInfo(serverName, ticker.getTPS());
            }
        }, 0L, 1L);

        DebugSender.sendDebug(DebugType.PLUGIN, "core loaded", "Core");
    }

    public void onDisable() {
        try {
            SQL.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


