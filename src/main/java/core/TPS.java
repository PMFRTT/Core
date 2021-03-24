package core;

import core.core.CoreMain;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TPS {

    private static int tps;
    private static CoreMain main;

    public TPS(CoreMain main) {
        TPS.main = main;
        calculateTPS();
    }

    private void calculateTPS() {
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

    public int getTPS(){
        return tps;
    }

}
