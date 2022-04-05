package core.core;

import core.TPS;
import core.Utils;
import org.bukkit.Bukkit;

public class CoreServerInfo {

    private static final CoreMain main = CoreHandler.getMain();

    public static void startUpdater(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                TPS.calculateTPS();
                Utils.addServerInfo(CoreHandler.getGuestPlugin().getName(), TPS.getRecentTickRate(), TPS.getRecentTickTime());
            }
        }, 0L, 1L);
    }

}
