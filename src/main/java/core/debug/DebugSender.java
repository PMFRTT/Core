package core.debug;

import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static core.chat.Color.colorizeHex;

public class DebugSender {

    public static void sendDebug(DebugType type, String message) {
        if (CoreMain.sqlConfig.getConfigbyName("debug").equals("1")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (CoreMain.rankUpdater.isDev(player)) {
                    player.sendMessage(colorizeHex(getDebugPrefix(type) + message));
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
    }

    private static String getDebugPrefix(DebugType type) {
        String color = DebugType.getDebugColor(type);
        return "[" + color + type.toString() + "&f]: &8";
    }

    private static String getDebugPrefix(DebugType type, String name) {
        String color = DebugType.getDebugColor(type);
        return "[" + color + type.toString() + " - " + name + "&f]: &8";
    }

}
