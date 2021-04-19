package core.hotbar;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public abstract class HotbarManager {

    private final Plugin plugin;

    private static final HashMap<String, HotbarScheduler> hotbarSchedulers = new HashMap<String, HotbarScheduler>();

    public HotbarManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public static HotbarScheduler getHotbarScheduler(Player player) {
        return hotbarSchedulers.get(player.getDisplayName());
    }

    public void createHotbarScheduler(Player player, HotbarScheduler hotbarScheduler) {
        hotbarSchedulers.put(player.getDisplayName(), hotbarScheduler);
    }

}
