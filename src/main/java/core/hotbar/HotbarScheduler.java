package core.hotbar;

import core.core.CoreSendStringPacket;
import core.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class HotbarScheduler {

    private final Integer TIMEOUT_TICKS = 30;

    private final List<String> scheduledMessages = new ArrayList<String>();
    private final List<Integer> scheduledTimeouts = new ArrayList<Integer>();
    private boolean hasScheduledMessage = false;
    private final String playerName;

    private final Plugin plugin;
    private String defaultMessage;
    private Timer timer = null;

    private boolean running = false;
    private boolean timed = false;

    public HotbarScheduler(Plugin plugin, String defaultMessage, String playerName) {
        this.plugin = plugin;
        this.defaultMessage = defaultMessage;
        this.timed = false;
        this.playerName = playerName;
    }

    public HotbarScheduler(Plugin plugin, Timer timer, String playerName) {
        this.plugin = plugin;
        this.timer = timer;
        this.timed = true;
        this.playerName = playerName;
    }

    public void scheduleMessage(String message, Integer timeout) {
        if (!this.scheduledMessages.contains(message)) {
            this.scheduledMessages.add(message);
            this.scheduledTimeouts.add(timeout);
            this.hasScheduledMessage = true;
        }
    }

    public void startScheduler(boolean resume) {
        if (!running) {
            running = true;
            if (!resume) {
                clear();
            }
            runScheduler();
        }
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
        this.timed = true;
    }

    public void removeTimer() {
        this.timer = null;
        this.timed = false;
    }

    public void killScheduler(boolean clear) {
        if (clear) {
            clear();
        }
        running = false;
    }

    private void runScheduler() {
        this.running = true;
        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getPlayer(playerName) != null) {
                    if (running) {
                        Player player = Bukkit.getPlayer(playerName);
                        assert player != null;
                        if (hasScheduledMessage) {
                            if (scheduledTimeouts.get(0) != 0) {
                                CoreSendStringPacket.sendPacketToHotbar(player, scheduledMessages.get(0));
                                scheduledTimeouts.set(0, scheduledTimeouts.get(0) - 1);
                            } else {
                                scheduledMessages.remove(0);
                                scheduledTimeouts.remove(0);
                                hasScheduledMessage = !scheduledMessages.isEmpty();
                            }
                        } else {
                            if (timed) {
                                CoreSendStringPacket.sendPacketToHotbar(player, timer.getTimerMessage());
                            } else {
                                CoreSendStringPacket.sendPacketToHotbar(player, defaultMessage);
                            }
                        }
                    } else {
                        cancel();
                    }
                }
            }

        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void clear() {
        this.hasScheduledMessage = false;
        this.defaultMessage = "";
        this.scheduledMessages.clear();
    }

    public void scheduleRepeatingMessage(String message, Integer distanceBetweenMessages, Integer duration, Integer delay) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(playerName))) {
                    scheduleMessage(message, duration);
                }
            }
        }, delay, distanceBetweenMessages);
    }

}
