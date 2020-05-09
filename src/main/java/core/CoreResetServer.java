package core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;

public class CoreResetServer {

    private static CoreMain corePlugin;

    public CoreResetServer(CoreMain corePlugin) {
        this.corePlugin = corePlugin;
    }

    public static void resetServer(String serverName, Boolean resetPositions) {

        File locations = null;

        for (Player p : Bukkit.getOnlinePlayers()) {
            CoreBungeeCordClient.moveToServer(p, "Lobby");
            CoreSendStringPacket.sendPacketToTitle(p, Utils.colorize("Fallback Server"), Utils.colorize("Bitte warte, während der &b" + serverName + "-Server &fneustartet"));
        }

        try {
            locations = new File(corePlugin.getDataFolder().getParentFile().getAbsolutePath() + "/SetPosition", "locations.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resetPositions) {
            if (locations != null) {
                if (locations.exists()) {
                    locations.delete();
                }
            }


            Bukkit.getScheduler().scheduleSyncDelayedTask(corePlugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.spigot().restart();
                }
            }, 40L);
        }


    }
}
