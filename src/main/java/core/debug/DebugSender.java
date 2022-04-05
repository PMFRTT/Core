package core.debug;

import core.config.ConfigHandler;
import core.hotbar.HotbarManager;
import core.ranks.RankUpdater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static core.chat.Color.colorizeHex;

public class DebugSender {

    private static final Integer DEBUGTIMEOUT = 50;

    public static void sendDebug(DebugType type, String message) {
        if (ConfigHandler.getDataset().getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (RankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type) + message));
                }
            }
        }
        else if(ConfigHandler.getDataset().getConfigbyName("debug").equals("2")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (RankUpdater.isDev(player)) {
                    HotbarManager.getHotbarScheduler(player).scheduleMessage(colorizeHex(getDebugPrefix(type) + message), DEBUGTIMEOUT);
                }
            }
        }
    }

    public static void sendDebug(DebugType type, String message, String name) {
        if (ConfigHandler.getDataset().getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (RankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type, name) + message));

                }
            }
        }
        else if(ConfigHandler.getDataset().getConfigbyName("debug").equals("2")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (RankUpdater.isDev(player)) {
                    HotbarManager.getHotbarScheduler(player).scheduleMessage(colorizeHex(getDebugPrefix(type, name) + message), DEBUGTIMEOUT);
                }
            }
        }
    }

    public static void sendDebug(DebugType type, String message, String name, String thread) {
        if (ConfigHandler.getDataset().getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (RankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type, name, thread) + message));
                }
            }
        }else if(ConfigHandler.getDataset().getConfigbyName("debug").equals("2")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (RankUpdater.isDev(player)) {
                    HotbarManager.getHotbarScheduler(player).scheduleMessage(colorizeHex(getDebugPrefix(type, name, thread) + message), DEBUGTIMEOUT);
                }
            }
        }
    }


    private static String getDebugPrefix(DebugType type) {
        String color = DebugType.getDebugColor(type);
        return "[" + color + type.toString() + "&f]: &8";
    }

    private static String getDebugPrefix(DebugType type, String name) {
        String color = DebugType.getDebugColor(type);
        return "[" + color + type.toString() + " - " + name + "&f]: &8";
    }

    private static String getDebugPrefix(DebugType type, String name, String thread) {
        String color = DebugType.getDebugColor(type);
        return "[" + color + type.toString() + " - " + name + "&8" + thread + "&f]: &8";
    }

}
