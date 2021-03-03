package core;

import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;

public class CoreDebug {
    private static CoreMain main;

    public CoreDebug(CoreMain main) {
        CoreDebug.main = main;
    }

    public static double tps;

    public static ArrayList<String> getDebugInfo() {

        ArrayList<String> debugStrings = new ArrayList<String>();
        debugStrings.add("players: " + Bukkit.getOnlinePlayers().size());
        debugStrings.add("tps: " + tps);
        debugStrings.add("plugins: " + Arrays.toString(Bukkit.getServer().getPluginManager().getPlugins()));
        //debugStrings.add("uptime: " + Utils.formatTimerTime(uptime));

        return debugStrings;
    }

    public static void getTPS() {

        BukkitTask runnable = new BukkitRunnable() {
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
        }.runTaskTimer(main, 0L, 1L);
    }

}
