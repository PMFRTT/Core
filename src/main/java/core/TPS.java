package core;

import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class TPS {

    private static float tps;
    private static float tickTime;
    private static CoreMain main;
    private static boolean below18 = false;
    private static boolean below10 = false;

    private static final  ArrayList<Float> recentTickRate = new ArrayList<Float>();
    private static final  ArrayList<Float> recentTickTime = new ArrayList<Float>();


    public TPS(CoreMain main){
        TPS.main = main;
        calculateTPS();
    }

    private void calculateTPS(){

        BukkitTask runnable = new BukkitRunnable() {

            long sec;
            long oldsec;


            @Override
            public void run() {
                sec = System.currentTimeMillis();
                tickTime = sec - oldsec;
                tps = 1000 / tickTime;

                if(recentTickRate.size() > 20){
                    recentTickRate.remove(0);
                }
                if(recentTickTime.size() > 20){
                    recentTickTime.remove(0);
                }
                recentTickTime.add(tickTime);
                recentTickRate.add(1000 / tickTime);

                oldsec = System.currentTimeMillis();
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public float getTPS() {
        checkTPS();
        Utils.checkMemory();
        return tps;
    }

    public float getTickTime(){
        return tickTime;
    }

    public double getRecentTickRate(){
        return recentTickRate.stream().mapToDouble(d -> d).average().orElse(0.0);
    }

    public double getRecentTickTime(){
        return recentTickTime.stream().mapToDouble(d -> d).average().orElse(0.0);
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
