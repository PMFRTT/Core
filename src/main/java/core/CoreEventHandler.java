package core;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class CoreEventHandler implements Listener {

    private CoreMain corePlugin;
    private CoreAccessPermissionFile accessPermissionFile;

    public CoreEventHandler(CoreMain corePlugin, CoreAccessPermissionFile accessPermissionFile){
        this.corePlugin = corePlugin;
        this.accessPermissionFile = accessPermissionFile;
    }

    public void initialize(){
        Bukkit.getPluginManager().registerEvents(this, corePlugin);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        accessPermissionFile.readFile();
        accessPermissionFile.createPermissionSave(e.getPlayer());
        e.setJoinMessage(Utils.getJoinPrefix(corePlugin.getName(), e.getPlayer()));
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
        if (corePlugin.showAdvancements) {
            Advancement a = e.getAdvancement();
            Player p = e.getPlayer();

            String temp = a.getKey().getKey();

            if (!temp.contains("recipes/")) {
                if (!temp.contains("root")) {
                    System.out.println(temp);
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
