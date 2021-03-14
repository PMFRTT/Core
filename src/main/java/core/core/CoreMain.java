package core.core;


import core.CoreDebug;
import core.Utils;
import core.bungee.CoreBungeeCordClient;
import core.permissions.CoreAccessPermissionFile;
import core.permissions.CorePermissionCommandListener;
import core.register.RegisterCommandListener;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public final class CoreMain extends JavaPlugin implements Listener {

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

        CoreAccessPermissionFile accessPermissionFile = new CoreAccessPermissionFile(this);
        CoreBungeeCordClient bungeeCordClient = new CoreBungeeCordClient(this);
        CoreEventHandler coreEventHandler = new CoreEventHandler(this, accessPermissionFile);
        CoreResetServer coreResetServer = new CoreResetServer(this, bungeeCordClient);
        CoreDebug coreDebug = new CoreDebug(this);
        Utils utils = new Utils(this);

        CoreBungeeCordClient.loadServers();
        coreEventHandler.initialize();
        Utils.changeGamerule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        CoreCommandListener coreCommandExecutor = new CoreCommandListener(this, bungeeCordClient);
        CorePermissionCommandListener corePermissionCommandExecutor = new CorePermissionCommandListener(this, accessPermissionFile);
        RegisterCommandListener registerCommandListener = new RegisterCommandListener(this);

        Objects.requireNonNull(getCommand("Gamemode")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Weather")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Time")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Core")).setExecutor(coreCommandExecutor);
        Objects.requireNonNull(getCommand("Allow")).setExecutor(corePermissionCommandExecutor);
        Objects.requireNonNull(getCommand("Disallow")).setExecutor(corePermissionCommandExecutor);
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

}


