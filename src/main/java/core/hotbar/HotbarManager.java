package core.hotbar;

import core.core.CoreHandler;
import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HotbarManager {

    private static final CoreMain plugin = CoreHandler.getMain();

    private static final HashMap<String, HotbarScheduler> hotbarSchedulers = new HashMap<>();


    public static void init() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            createHotbarScheduler(player, new HotbarScheduler(plugin, "", player.getDisplayName()));
        }
    }

    public static HotbarScheduler getHotbarScheduler(Player player) {
        return hotbarSchedulers.get(player.getDisplayName());
    }

    public static void createHotbarScheduler(Player player, HotbarScheduler hotbarScheduler) {
        if (!contains(player)) {
            hotbarSchedulers.put(player.getDisplayName(), hotbarScheduler);
        }
    }

    public static boolean contains(Player player) {
        return hotbarSchedulers.containsKey(player.getDisplayName());
    }

    public static HashMap<String, HotbarScheduler> getAllSchedulers() {
        return hotbarSchedulers;
    }

}
