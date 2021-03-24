package core.core;

import core.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;

public class CoreEventHandler implements Listener {

    private final CoreMain corePlugin;

    public CoreEventHandler(CoreMain corePlugin){
        this.corePlugin = corePlugin;
        init();
    }

    public void init(){
        Bukkit.getPluginManager().registerEvents(this, corePlugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(Utils.getJoinPrefix("Server", e.getPlayer()));
        Utils.addServerInfo(CoreMain.getPlugin().getName(), 0);
    }

    @EventHandler
    private void onPlayerDisconnect(PlayerQuitEvent e){
        e.setQuitMessage(Utils.getDisconnectPrefix("Server", e.getPlayer()));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        String s = e.getMessage();
        Player p = e.getPlayer();
        Set<Player> r = e.getRecipients();
        for (Player player : r) {
            player.sendMessage(Utils.getChatPrefix(p) + Utils.colorize(s));
        }
    }

    @EventHandler
    public void onAdvancementGet(PlayerAdvancementDoneEvent e) {
        World world = Bukkit.getWorld("world");
        assert world != null;
        if (world.getGameRuleValue(GameRule.ANNOUNCE_ADVANCEMENTS)) {
            Advancement a = e.getAdvancement();
            Player p = e.getPlayer();

            String temp = a.getKey().getKey();

            if (!temp.contains("recipes/")) {
                if (!temp.contains("root")) {
                    temp = temp.substring(temp.indexOf("/"));
                    temp = temp.substring(1);
                    temp = temp.replace("_", " ");
                    temp = WordUtils.capitalize(temp);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Utils.getPrefix("Advancement") + Utils.colorize("&b" + p.getDisplayName() + " &fhat das Advancement &a" + temp + "&f erreicht!"));
                    }
                }
            }
        }

    }
}
