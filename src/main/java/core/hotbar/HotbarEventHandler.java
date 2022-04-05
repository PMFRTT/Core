package core.hotbar;

import core.core.CoreHandler;
import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HotbarEventHandler implements Listener {

    private final CoreMain main = CoreHandler.getMain();

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(HotbarManager.contains(e.getPlayer())){
            HotbarManager.getHotbarScheduler(e.getPlayer()).startScheduler(true);
        }
    }

}
