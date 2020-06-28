package core.core;


import core.Utils;
import core.bungee.CoreBungeeCordClient;
import core.permissions.CoreAccessPermissionFile;
import core.permissions.CorePermissionCommandListener;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public final class CoreMain extends JavaPlugin implements Listener {

    public static HashMap<UUID, PermissionAttachment> permissionAttachmentHashMap = new HashMap<>();
    public static boolean showAdvancements = true;
    private static String serverName = "loading";
    public static double tps;
    private static int uptime;

    public void onEnable() {

        CoreAccessPermissionFile accessPermissionFile = new CoreAccessPermissionFile(this);
        CoreBungeeCordClient bungeeCordClient = new CoreBungeeCordClient(this);
        CoreEventHandler coreEventHandler = new CoreEventHandler(this, accessPermissionFile);
        CoreResetServer coreResetServer = new CoreResetServer(this, bungeeCordClient);
        Utils utils = new Utils(this);

        CoreBungeeCordClient.loadServers();
        coreEventHandler.initialize();
        Utils.changeGamerule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        CoreCommandListener coreCommandExecutor = new CoreCommandListener(this, bungeeCordClient);
        CorePermissionCommandListener corePermissionCommandExecutor = new CorePermissionCommandListener(this, accessPermissionFile);

        getCommand("Gamemode").setExecutor(coreCommandExecutor);
        getCommand("Weather").setExecutor(coreCommandExecutor);
        getCommand("Time").setExecutor(coreCommandExecutor);
        getCommand("Core").setExecutor(coreCommandExecutor);
        getCommand("Allow").setExecutor(corePermissionCommandExecutor);
        getCommand("Disallow").setExecutor(corePermissionCommandExecutor);
        getCommand("hub").setExecutor(coreCommandExecutor);
        getCommand("Heal").setExecutor(coreCommandExecutor);
        getCommand("Difficulty").setExecutor(coreCommandExecutor);
        getCommand("Permissions").setExecutor(corePermissionCommandExecutor);
        getCommand("tps").setExecutor(coreCommandExecutor);
        getCommand("Advancements").setExecutor(coreCommandExecutor);
        getCommand("ping").setExecutor(coreCommandExecutor);
        getCommand("setHP").setExecutor(coreCommandExecutor);
        getCommand("invsee").setExecutor(coreCommandExecutor);
        getCommand("teleport").setExecutor(coreCommandExecutor);
        getCommand("reload").setExecutor(coreCommandExecutor);
        getCommand("reboot").setExecutor(coreCommandExecutor);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                addServerInfo(serverName);
            }
        }, 0L, 1L);

        Random random = new Random();

        BukkitTask runnable = new BukkitRunnable(){
            @Override
            public void run(){
                for(Player player : Bukkit.getOnlinePlayers()){
                    CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&cError"), Utils.colorize("Bitte kontrolliere die Console (0xc001 Debug)"));
                   for(String s : getDebugInfo()){
                       System.out.println(s);
                       player.sendMessage(Utils.getErrorPrefix() + s);
                   }
                }
            }
        }.runTaskTimer(this, random.nextInt(1000), random.nextInt(10000));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                uptime++;
            }
        }, 0, 20);

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

            List<String> headerList = new ArrayList<String>();
            headerList.add(Utils.colorize("&0--&8---&7---&f[&eP&aM&bF&9R&dT&cT&f-Server-Network]&7---&8---&0--&f"));
            headerList.add("");
            headerList.add(Utils.colorize("Moin &b" + player.getDisplayName() + "&f!"));
            headerList.add(Utils.colorize("Es ist &b" + dateFormatted));
            headerList.add(Utils.colorize("&fDu befindest dich auf &b" + serverName));
            headerList.add("   ");


            List<String> footerList = new ArrayList<String>();
            footerList.add("   ");
            footerList.add(Utils.colorize("&8Server-Software: &e" + Bukkit.getServer().getVersion()));
            footerList.add(Utils.colorize("&8Server-TPS: &e" + new DecimalFormat("#.#").format(tps) + "&8 Ticks per second"));
            footerList.add(Utils.colorize("&7" + Bukkit.getIp() + "&f:&7" + Bukkit.getServer().getPort() + " (&e" + pingablePlayer.getHandle().ping + "&7ms)"));

            String header = StringUtils.join(headerList, "\n");
            String footer = StringUtils.join(footerList, "\n");

            player.setPlayerListHeaderFooter(header, footer);
        }
    }

    public static ArrayList<String> getDebugInfo(){

        ArrayList<String> debugStrings = new ArrayList<String>();
        debugStrings.add("players: " + Bukkit.getOnlinePlayers().size());
        debugStrings.add("tps: " + tps);
        debugStrings.add("plugins: " + Arrays.toString(Bukkit.getServer().getPluginManager().getPlugins()));
        debugStrings.add("uptime:" + Utils.formatTimerTime(uptime));

        return  debugStrings;
    }


}


