package core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class CoreResetServer {


    private static CoreMain corePlugin;

    public CoreResetServer(CoreMain corePlugin) {
        this.corePlugin = corePlugin;
    }


    public static void resetServer(String serverName, Boolean resetPositions) {

        //sends every Player to Lobby
        for (Player p : Bukkit.getOnlinePlayers()) {
            CoreBungeeCordClient.moveToServer(p, "Lobby");
            CoreSendStringPacket.sendPacketToTitle(p, Utils.colorize("Fallback Server"), Utils.colorize("Bitte warte, während der " + serverName + "-Server neustartet"));
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //resets Positionfile if boolean true
        if (resetPositions) {
            try {
                File locations = new File(corePlugin.getDataFolder().getParentFile().getAbsolutePath() + "/SetPosition", "locations.txt");
                if (locations.exists()) {
                    locations.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //restarts server
            Bukkit.spigot().restart();

        }


    }
}
