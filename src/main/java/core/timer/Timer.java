package core.timer;

import core.Utils;
import core.core.CoreSendStringPacket;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Timer {

    private TimerType timerType;

    private int seconds;
    private int ticks;

    private boolean running = false;
    private boolean single;
    private final boolean hidden;

    private final String pausedString;
    private final String runningString;

    private final Plugin plugin;
    private BukkitScheduler scheduler;

    private String msg = "";

    private Player player;
    private String timerReady;

    public Timer(Plugin plugin, TimerType timerType, String runningString, String pausedString, boolean hidden) {
        this.timerType = timerType;
        this.plugin = plugin;
        this.runningString = runningString;
        this.pausedString = pausedString;
        this.hidden = hidden;
        init();
    }

    public Timer(Plugin plugin, TimerType timerType, String runningString, String pausedString, boolean hidden, String timerReady, Player player) {
        this.timerType = timerType;
        this.plugin = plugin;
        this.runningString = runningString;
        this.pausedString = pausedString;
        this.hidden = hidden;
        this.player = player;
        this.timerReady = timerReady;
        init();
    }

    private void init() {
        this.scheduler = this.plugin.getServer().getScheduler();
        runMainTimer();
        DebugSender.sendDebug(DebugType.TIMER, "timer init method complete");
    }

    private void runMainTimer() {
        scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                if (running) {
                    if (timerType.equals(TimerType.INCREASING)) {
                        ticks++;
                        msg = Utils.colorize(runningString + Utils.formatTimerTimeTicks(ticks));
                        if (ticks % 20 == 0) {
                            seconds++;
                        }
                    } else if (timerType.equals(TimerType.DECREASING)) {
                        ticks--;
                        if (single) {
                            msg = Utils.colorize("Du hast noch &b" + Utils.formatTimerTimeTicks(ticks) + " &fZeit");
                        } else {
                            msg = Utils.colorize("Ihr habt noch &b" + Utils.formatTimerTimeTicks(ticks) + " &fZeit");
                        }
                        if (ticks % 20 == 0) {
                            seconds--;
                        }
                        if (ticks == 0) {
                            if(player != null){
                                CoreSendStringPacket.sendPacketToTitle(player, "", timerReady);
                            }
                            pause();
                        }
                    }
                } else {
                    msg = Utils.colorize(pausedString);
                }
            }
        }, 0, 1);
    }

    public void pause() {
        if (this.running) {
            this.running = false;
            DebugSender.sendDebug(DebugType.TIMER, "timer paused");
        }
    }

    public void resume() {
        if (!this.running) {
            this.running = true;
            DebugSender.sendDebug(DebugType.TIMER, "timer resumed");
        }
    }

    public boolean isPaused() {
        return !this.running;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        this.ticks = seconds * 20;
        DebugSender.sendDebug(DebugType.TIMER, "timer set to " + seconds + " seconds");
    }

    public void addSeconds(int seconds) {
        this.seconds += seconds;
        this.ticks = this.seconds * 20;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public int getTicks() {
        return this.ticks;
    }

    public void setTimerType(TimerType timerType) {
        this.timerType = timerType;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean getSingle() {
        return this.single;
    }

    public String getTimerMessage(){
        return this.msg;
    }

}
