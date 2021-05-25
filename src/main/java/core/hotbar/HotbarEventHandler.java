package core.hotbar;

import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HotbarEventHandler implements Listener {

    private final CoreMain corePlugin;

    public HotbarEventHandler(CoreMain corePlugin) {
        this.corePlugin = corePlugin;
        init();
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, corePlugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(CoreMain.hotbarManager.contains(e.getPlayer())){
            CoreMain.hotbarManager.getHotbarScheduler(e.getPlayer()).startScheduler(true);
        }
    }

}
