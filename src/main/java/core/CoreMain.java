package core;


import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public final class CoreMain extends JavaPlugin implements Listener {

    public static HashMap<UUID, PermissionAttachment> permissionAttachmentHashMap = new HashMap<>();
    public static boolean showAdvancements = true;
    private static String serverName = "loading";
    public static double tps;

    public void onEnable() {

        CoreAccessPermissionFile accessPermissionFile = new CoreAccessPermissionFile(this);
        CoreBungeeCordClient bungeeCordClient = new CoreBungeeCordClient(this);
        CoreEventHandler coreEventHandler = new CoreEventHandler(this, accessPermissionFile);
        CoreResetServer coreResetServer = new CoreResetServer(this);
        Utils utils = new Utils(this);

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
        getCommand("setHP").setExecutor(executor);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                addServerInfo(serverName);
            }
        }, 0L, 1L);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            long sec;
            long currentSec;
            int ticks;

            @Override
            public void run() {
                sec = (System.currentTimeMillis() / 1000);

                if (currentSec == sec) {
                    ticks++;
                } else {
                    currentSec = sec;
                    tps = (tps == 0 ? ticks : (((tps + ticks) + 1) / 2));
                    ticks = 0;
                }
            }
        }, 0, 1);
    }

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        CoreMain.plugin = plugin;
        serverName = plugin.getName();
    }


    public static void addServerInfo(String serverName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CraftPlayer pingablePlayer = (CraftPlayer) player;
            Date date = new Date();
            String dateFormatted = new SimpleDateFormat("HH:mm").format(date);

            List<String> headerList = new ArrayList<String>() {{
                add(Utils.colorize("&0--&8---&7---&f[&eP&aM&bF&9R&dT&cT&f-Server-Network]&7---&8---&0--&f"));
                add("");
                add(Utils.colorize("Moin &b" + player.getDisplayName() + "&f!"));
                add(Utils.colorize("Es ist &b" + dateFormatted));
                add(Utils.colorize("&fDu befindest dich auf &b" + serverName));
                add("   ");
            }};

            List<String> footerList = new ArrayList<String>() {{
                add("   ");
                add(Utils.colorize("&8Server-Software: &e" + Bukkit.getServer().getVersion()));
                add(Utils.colorize("&8Server-TPS: &e" + new DecimalFormat("#.#").format(tps) + "&8 Ticks per second"));
                add(Utils.colorize("&7" + Bukkit.getIp() + "&f:&7" + Bukkit.getServer().getPort() + " (&e" + pingablePlayer.getHandle().ping + "&7ms"));
            }};

            String header = StringUtils.join(headerList, "\n");
            String footer = StringUtils.join(footerList, "\n");

            player.setPlayerListHeaderFooter(header, footer);
        }
    }
}


