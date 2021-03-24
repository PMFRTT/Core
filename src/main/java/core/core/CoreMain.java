package core.core;


import core.CoreDebug;
import core.Utils;
import core.bungee.CoreBungeeCordClient;
import core.permissions.CorePermissionCommandListener;
import core.permissions.PermissionConverter;
import core.sql.MySQL;
import core.sql.MySQLBungee;
import core.sql.MySQLPermissions;
import core.sql.SQLConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public final class CoreMain extends JavaPlugin {

    public MySQL SQL;

    public MySQLPermissions mySQLPermissions;
    public MySQLBungee mySQLBungee;
    public SQLConfig sqlConfig;

    public PermissionConverter permissionConverter;
    public static HashMap<UUID, PermissionAttachment> permissionAttachmentHashMap = new HashMap<>();
    public static boolean showAdvancements = true;
    private static String serverName = "loading";
    public static double tps;
    private static JavaPlugin plugin;
    private final Utils utils = new Utils(this);

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        CoreMain.plugin = plugin;
        serverName = plugin.getName();
    }

    public void onEnable() {

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
        CoreDebug coreDebug = new CoreDebug(this);
        Utils utils = new Utils(this);

        Utils.changeGamerule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        CoreCommandListener coreCommandExecutor = new CoreCommandListener(this, bungeeCordClient);
        CorePermissionCommandListener corePermissionCommandExecutor = new CorePermissionCommandListener(this);

        Objects.requireNonNull(getCommand("Gamemode")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Weather")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Time")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Core")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Allow")).setExecutor(corePermissionCommandExecutor);
        Objects.requireNonNull(getCommand("Deny")).setExecutor(corePermissionCommandExecutor);
        Objects.requireNonNull(getCommand("hub")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Heal")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Difficulty")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Permissions")).setExecutor(corePermissionCommandExecutor);
        Objects.requireNonNull(getCommand("tps")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Advancements")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("ping")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("setHP")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("invsee")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("teleport")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("reload")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("reboot")).setExecutor(coreCommandExecutor);
        CoreDebug.getTPS();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                tps = CoreDebug.tps;
                Utils.addServerInfo(serverName, tps);
            }
        }, 0L, 1L);
    }

    public void onDisable() {
        try {
            SQL.disconnect();
        } catch (SQLException ignored) {
        }
    }

}


