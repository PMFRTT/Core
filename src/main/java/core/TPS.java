package core;

import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TPS {

    private static int tps;
    private static CoreMain main;
    private static boolean below18 = false;
    private static boolean below10 = false;

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

    public int getTPS() {
        checkTPS();
        Utils.checkMemory();
        return tps;
    }

    private void checkTPS() {
        if (tps < 18 && !below18) {
            DebugSender.sendDebug(DebugType.SERVER, "tps below 18");
            below18 = true;
        } else if (tps < 10 && !below10) {
            DebugSender.sendDebug(DebugType.SERVER, "tps below &c10");
            below10 = true;
        } else if (tps > 12 && below10) {
            below10 = false;
        } else if (tps > 20 && (below18)) {
            DebugSender.sendDebug(DebugType.SERVER, "tps recovered");
            below18 = false;
            below10 = false;
        }
    }

}
