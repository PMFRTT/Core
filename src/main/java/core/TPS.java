package core;

import core.core.CoreHandler;
import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;

import java.util.ArrayList;

public class TPS {

    private static float tps;
    private static float tickTime;
    private static final CoreMain main = CoreHandler.getMain();
    private static boolean below18 = false;
    private static boolean below10 = false;

    private static final ArrayList<Float> recentTickRate = new ArrayList<Float>();
    private static final ArrayList<Float> recentTickTime = new ArrayList<Float>();

    private static long oldsec;

    public static void calculateTPS() {
        long sec = System.currentTimeMillis();
        tickTime = sec - oldsec;
        tps = 1000 / tickTime;

        if (recentTickRate.size() > 20) {
            recentTickRate.remove(0);
        }
        if (recentTickTime.size() > 20) {
            recentTickTime.remove(0);
        }
        recentTickTime.add(tickTime);
        recentTickRate.add(1000 / tickTime);

        oldsec = System.currentTimeMillis();
    }

    public static float getTPS() {
        checkTPS();
        Utils.checkMemory();
        return tps;
    }

    public static float getTickTime() {
        return tickTime;
    }

    public static double getRecentTickRate() {
        return recentTickRate.stream().mapToDouble(d -> d).average().orElse(0.0);
    }

    public static double getRecentTickTime() {
        return recentTickTime.stream().mapToDouble(d -> d).average().orElse(0.0);
    }

    private static void checkTPS() {
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
