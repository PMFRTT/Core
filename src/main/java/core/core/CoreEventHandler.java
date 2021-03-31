package core.core;

import core.Utils;
import core.ranks.Rank;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class CoreEventHandler implements Listener {

    private final CoreMain corePlugin;

    public CoreEventHandler(CoreMain corePlugin) {
        this.corePlugin = corePlugin;
        init();
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, corePlugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        CoreMain.mySQLPermissions.createPlayer(e.getPlayer());
        CoreMain.mySQLRanks.createPlayer(e.getPlayer());
        e.setJoinMessage(Utils.getJoinPrefix("Server", e.getPlayer()));
        Utils.addServerInfo(CoreMain.getPlugin().getName(), 0);
    }

    @EventHandler
    private void onPlayerDisconnect(PlayerQuitEvent e) {
        e.setQuitMessage(Utils.getDisconnectPrefix("Server", e.getPlayer()));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        switch(Rank.convertIntToRank(CoreMain.mySQLRanks.getRank(p.getUniqueId()))){
            case OWNER -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "OWNER" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.LIGHT_PURPLE + e.getMessage());
            case DEV -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "DEV" + ChatColor.DARK_GRAY + "] " + ChatColor.AQUA + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + e.getMessage());
            case ADMIN -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + e.getMessage());
            case PLUS -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "GAMER" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + e.getMessage());
            case NORMAL -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "NOOB" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + e.getMessage());
            case BANNED -> {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Utils.colorize("&cDu wurdest vom Chat ausgeschlossen!"));
            }
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
