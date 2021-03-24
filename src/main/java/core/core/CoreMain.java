package core.core;


import core.TPS;
import core.Utils;
import core.bungee.CoreBungeeCordClient;
import core.commands.MainCommandListener;
import core.permissions.PermissionConverter;
import core.sql.MySQL;
import core.sql.MySQLBungee;
import core.sql.MySQLPermissions;
import core.sql.SQLConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public final class CoreMain extends JavaPlugin {

    public MySQL SQL;

    public MySQLPermissions mySQLPermissions;
    public MySQLBungee mySQLBungee;
    public SQLConfig sqlConfig;

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

        this.SQL = new MySQL();
        this.mySQLPermissions = new MySQLPermissions(this);
        this.mySQLBungee = new MySQLBungee(this);
        this.sqlConfig = new SQLConfig(this);
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
            for (Player player : Bukkit.getOnlinePlayers()) {
                mySQLPermissions.createPlayer(player);
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
    }

    public void onDisable() {
        try {
            SQL.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


