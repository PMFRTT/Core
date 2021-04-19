package core.hotbar;

import core.core.CoreSendStringPacket;
import core.timer.Timer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class HotbarScheduler {

    private final List<String> scheduledMessages = new ArrayList<String>();
    private boolean hasScheduledMessage = false;
    private int schedulerTimeout = 0;
    private final Player player;

    private final Plugin plugin;
    private String defaultMessage;
    private Timer timer = null;

    private boolean running = false;
    private boolean timed = false;

    public HotbarScheduler(Plugin plugin, String defaultMessage, Player player) {
        this.plugin = plugin;
        this.defaultMessage = defaultMessage;
        this.timed = false;
        this.player = player;
    }

    public HotbarScheduler(Plugin plugin, Timer timer, Player player) {
        this.plugin = plugin;
        this.timer = timer;
        this.timed = true;
        this.player = player;
    }

    public void scheduleMessage(String message) {
        this.scheduledMessages.add(message);
        this.schedulerTimeout = 100;
        this.hasScheduledMessage = true;
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
                if (running) {
                    if (hasScheduledMessage) {
                        if (schedulerTimeout != 0) {
                            CoreSendStringPacket.sendPacketToHotbar(player, scheduledMessages.get(0));
                            schedulerTimeout--;
                        } else {
                            scheduledMessages.remove(0);
                            if (!scheduledMessages.isEmpty()) {
                                schedulerTimeout = 100;
                                hasScheduledMessage = true;
                            } else {
                                hasScheduledMessage = false;
                            }
                        }
                    } else {
                        assert player != null;
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

        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void clear() {
        this.hasScheduledMessage = false;
        this.defaultMessage = "";
        this.scheduledMessages.clear();
    }


}
