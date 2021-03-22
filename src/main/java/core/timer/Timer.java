package core.timer;

import core.Utils;
import core.core.CoreSendStringPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Timer {

    private TimerType timerType;

    private int seconds;
    private int ticks;

    private boolean running = false;
    private boolean single;

    private final String pausedString;
    private final String runningString;

    private final Plugin plugin;
    private BukkitScheduler scheduler;

    public Timer(Plugin plugin, TimerType timerType, String runningString, String pausedString) {
        this.timerType = timerType;
        this.plugin = plugin;
        this.runningString = runningString;
        this.pausedString = pausedString;
        init();
    }

    private void init() {
        this.scheduler = this.plugin.getServer().getScheduler();
        runMainTimer();
    }

    private void runMainTimer() {
        scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                String msg = null;
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
                            pause();
                        }
                    }
                } else {
                    msg = Utils.colorize(pausedString);
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    CoreSendStringPacket.sendPacketToHotbar(player, msg);
                }
            }
        }, 0, 1);
    }

    public void pause() {
        if (this.running) {
            this.running = false;
        }
    }

    public void resume() {
        if (!this.running) {
            this.running = true;
        }
    }

    public boolean isPaused(){
        return !this.running;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        this.ticks = seconds * 20;
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

}
