package core.core;

import core.Utils;
import core.chat.Color;
import core.debug.DebugSender;
import core.debug.DebugType;
import core.hotbar.HotbarScheduler;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;

import java.sql.SQLException;

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
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException, ClassNotFoundException {
        CoreMain.rankUpdater.updateAll();
        CoreMain.SQL.connect();
        CoreMain.mySQLPermissions.createPlayer(e.getPlayer());
        CoreMain.mySQLRanks.createPlayer(e.getPlayer());
        CoreMain.hotbarManager.createHotbarScheduler(e.getPlayer(), new HotbarScheduler(corePlugin, "", e.getPlayer().getDisplayName()));
        Utils.addServerInfo(CoreMain.getPlugin().getName(), 0);
        e.setJoinMessage(Utils.getJoinPrefix("Server", e.getPlayer()));
        DebugSender.sendDebug(DebugType.SERVER, "player joined");
    }

    @EventHandler
    private void onPlayerDisconnect(PlayerQuitEvent e) {
        e.setQuitMessage(Utils.getDisconnectPrefix("Server", e.getPlayer()));
        DebugSender.sendDebug(DebugType.SERVER, "player left");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        switch (Rank.convertIntToRank(CoreMain.mySQLRanks.getRank(p.getUniqueId()))) {
            case OWNER -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "OWNER" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + Color.colorizeHex(e.getMessage()));
            case DEV -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "DEV" + ChatColor.DARK_GRAY + "] " + ChatColor.AQUA + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + Color.colorizeHex(e.getMessage()));
            case ADMIN -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + Color.colorizeHex(e.getMessage()));
            case PLUS -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "GAMER" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + Color.colorizeHex(e.getMessage()));
            case NORMAL -> e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "NOOB" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + p.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + Color.colorizeHex(e.getMessage()));
            case BANNED -> {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Utils.colorize("&cDu wurdest vom Chat ausgeschlossen!"));
                DebugSender.sendDebug(DebugType.CHAT, "banned player tried to chat");
            }
        }


    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            DebugSender.sendDebug(DebugType.PLAYER, ((Player) e.getEntity()).getDisplayName() + " received damage");
        }
    }

    @EventHandler
    public void getPlayerFlying(PlayerToggleFlightEvent e){
        DebugSender.sendDebug(DebugType.PLAYER, e.getPlayer().getDisplayName() + " set flying to " + !e.getPlayer().isFlying());
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Player) {
            DebugSender.sendDebug(DebugType.PLAYER, ((Player) e.getEntity()).getDisplayName() + " died");
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            DebugSender.sendDebug(DebugType.PLAYER, ((Player) e.getDamager()).getDisplayName() + " damaged " + e.getEntity().getType().name());
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
