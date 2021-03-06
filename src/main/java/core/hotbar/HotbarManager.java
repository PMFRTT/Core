package core.hotbar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class HotbarManager {

    private final Plugin plugin;

    private static final HashMap<String, HotbarScheduler> hotbarSchedulers = new HashMap<String, HotbarScheduler>();

    public HotbarManager(Plugin plugin) {
        this.plugin = plugin;
        init();
    }

    public void init() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            createHotbarScheduler(player, new HotbarScheduler(plugin, "", player.getDisplayName()));
        }
    }

    public HotbarScheduler getHotbarScheduler(Player player) {
        return hotbarSchedulers.get(player.getDisplayName());
    }

    public void createHotbarScheduler(Player player, HotbarScheduler hotbarScheduler) {
        if (!contains(player)) {
            hotbarSchedulers.put(player.getDisplayName(), hotbarScheduler);
        }
    }

    public boolean contains(Player player) {
        return hotbarSchedulers.containsKey(player.getDisplayName());
    }

    public HashMap<String, HotbarScheduler> getAllSchedulers() {
        return hotbarSchedulers;
    }

}
