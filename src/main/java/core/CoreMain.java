package core;


import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;


public final class CoreMain extends JavaPlugin implements Listener {

    public static HashMap<UUID, PermissionAttachment> permissionAttachmentHashMap = new HashMap<>();
    public static boolean showAdvancements = true;

    public void onEnable() {

        CoreAccessPermissionFile accessPermissionFile = new CoreAccessPermissionFile(this);
        CoreBungeeCordClient bungeeCordClient = new CoreBungeeCordClient(this);
        CoreEventHandler coreEventHandler = new CoreEventHandler(this, accessPermissionFile);
        CoreResetServer coreResetServer = new CoreResetServer(this);

        CoreBungeeCordClient.loadServers();
        coreEventHandler.initialize();
        Utils.changeGamerule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        CoreCommands executor = new CoreCommands(this, accessPermissionFile);
        getCommand("Gamemode").setExecutor(executor);
        getCommand("Weather").setExecutor(executor);
        getCommand("Time").setExecutor(executor);
        getCommand("Core").setExecutor(executor);
        getCommand("Allow").setExecutor(executor);
        getCommand("Disallow").setExecutor(executor);
        getCommand("hub").setExecutor(executor);
        getCommand("Heal").setExecutor(executor);
        getCommand("Difficulty").setExecutor(executor);
        getCommand("Permissions").setExecutor(executor);
        getCommand("tps").setExecutor(executor);
        getCommand("Advancements").setExecutor(executor);
        getCommand("ping").setExecutor(executor);

    }

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        CoreMain.plugin = plugin;
        addServerInfo(plugin.getName());
    }

    public static void addServerInfo(String serverName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeaderFooter(Utils.colorize("Moin &b" + player.getDisplayName() + "&f!"), Utils.colorize("Du befindest dich auf &b" + serverName + "\n" +
                    "&8Server-Version: &e" + Bukkit.getServer().getVersion() + "\n&7" +
                    Bukkit.getIp() + "&f:&7" + Bukkit.getServer().getPort()));
        }
    }
}


