package core.debug;

import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static core.chat.Color.colorizeHex;

public class DebugSender {

    private static final Integer DEBUGTIMEOUT = 50;

    public static void sendDebug(DebugType type, String message) {
        if (CoreMain.sqlConfig.getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type) + message));
                }
            }
        }
        else if(CoreMain.sqlConfig.getConfigbyName("debug").equals("2")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    CoreMain.hotbarManager.getHotbarScheduler(player).scheduleMessage(colorizeHex(getDebugPrefix(type) + message), DEBUGTIMEOUT);
                }
            }
        }
    }

    public static void sendDebug(DebugType type, String message, String name) {
        if (CoreMain.sqlConfig.getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type, name) + message));

                }
            }
        }
        else if(CoreMain.sqlConfig.getConfigbyName("debug").equals("2")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    CoreMain.hotbarManager.getHotbarScheduler(player).scheduleMessage(colorizeHex(getDebugPrefix(type, name) + message), DEBUGTIMEOUT);
                }
            }
        }
    }

    public static void sendDebug(DebugType type, String message, String name, String thread) {
        if (CoreMain.sqlConfig.getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type, name, thread) + message));
                }
            }
        }else if(CoreMain.sqlConfig.getConfigbyName("debug").equals("2")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    CoreMain.hotbarManager.getHotbarScheduler(player).scheduleMessage(colorizeHex(getDebugPrefix(type, name, thread) + message), DEBUGTIMEOUT);
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
