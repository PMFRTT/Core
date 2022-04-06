package core.core;

import core.Utils;
import core.bungee.CoreBungeeCordClient;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CoreResetServer {

    private static final CoreMain main = CoreHandler.getMain();

    public static void resetServer(String serverName, Boolean resetPositions) {
        DebugSender.sendDebug(DebugType.SERVER, "server will be resetting");
        DebugSender.sendDebug(DebugType.BUNGEE, "moving all players");
        File locations = null;
        closePlayerConnection(serverName);

        try {
            locations = new File(main.getDataFolder().getParentFile().getAbsolutePath() + "/Position", "locations.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resetPositions && locations != null && locations.exists()) {
            locations.delete();
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> Bukkit.spigot().restart(), 40L);
    }

    private static void closePlayerConnection(String serverName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CoreBungeeCordClient.moveToServer(player, "LOBBYSERVER");
            CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("Fallback Server"), Utils.colorize("Bitte warte, während der &b" + serverName + "-Server &fneustartet"));
        }
    }


}

