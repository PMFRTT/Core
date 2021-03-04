package core;

import core.core.CoreMain;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static CoreMain corePlugin;

    public Utils(CoreMain corePlugin) {
        Utils.corePlugin = corePlugin;
    }

    private static final String[] rainbowStrings = {
            "&c", "&6", "&e", "&a", "&9", "&d", "&5"
    };


    public static String getPrefix(String pluginName) {
        return colorize("[&2" + pluginName + "&f]: ");
    }

    public static String getErrorPrefix() {
        return colorize("[&cError&f]: ");
    }

    public static String getResetPrefix() {
        return colorize("[&4Server Thread&f]: ");
    }

    public static String getChatPrefix(Player player) {
        if (player.hasPermission("core.canShowTPS")) {
            return colorize("[&c" + player.getDisplayName() + "&f]: ");
        } else {
            return colorize("[&b" + player.getDisplayName() + "&f]: ");
        }
    }

    public static String getJoinPrefix(String pluginName, Player player) {
        return getPrefix(pluginName) + colorize("&a" + player.getDisplayName() + " &fhat den Server betreten!");
    }

    public static String getDisconnectPrefix(String pluginName, Player player) {
        return getPrefix(pluginName) + colorize("&c" + player.getDisplayName() + " &fhat den Server verlassen!");
    }

    public static String getServerPrefix() {
        return "[" + colorize("&2Server") + colorize("&f]: ");
    }

    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessageToEveryone(String message) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public static String formatIngameTime(long ticks) {
        String time;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        ticks += 6000; //to normalize ticks
        ticks %= 24000;
        int hours = (int) ticks / 1000;
        int minutes = (int) ((ticks % 1000) / (100 / 6));
        String minutesFormatted = decimalFormat.format(minutes);
        String hoursFormatted = decimalFormat.format(hours);
        time = hoursFormatted + ":" + minutesFormatted;
        return time;
    }

    public static String formatTimerTime(int seconds) {
        String time;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;
        String secondsFormatted = decimalFormat.format(remainingSeconds);
        String minutesFormatted = decimalFormat.format(minutes);
        String hoursFormatted = decimalFormat.format(hours);
        time = hoursFormatted + ":" + minutesFormatted + ":" + secondsFormatted;
        return time;
    }

    public static String getRainbowString(String message) {
        String coloredMessage = "";
        Random random = new Random();
        for (Character c : message.toCharArray()) {
            coloredMessage = coloredMessage + rainbowStrings[random.nextInt(rainbowStrings.length)] + c;
        }
        return coloredMessage;
    }

    public static void heal(Player player) {


        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getHealth() < 19.5) {
                    if (player.getHealth() + 0.5 > 19.5) {
                        player.setHealth(20);
                    } else {
                        player.setHealth(player.getHealth() + 0.5);
                    }
                }
                if (player.getFoodLevel() < 19.5) {
                    if (player.getFoodLevel() + 0.5 > 19.5) {
                        player.setFoodLevel(20);
                    } else {
                        player.setFoodLevel(player.getFoodLevel() + 1);
                    }
                }

                if (player.getFoodLevel() > 19.5 && player.getHealth() > 19.5) {
                    cancel();
                }
            }

        }.runTaskTimer(corePlugin, 0L, 1L);
    }

    public static Entity getClosestEntity(Player player, double radius) {
        Location center = player.getLocation();
        Entity closestEntity = null;
        double closestDistance = 0.0;

        for (Entity entity : center.getWorld().getEntities()) {
            if (entity != player) {
                double distance = entity.getLocation().distanceSquared(center);
                if (closestEntity == null || distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }
        return closestEntity;
    }

    public static void changeGamerule(GameRule<Boolean> gameRule, boolean value) {

        for (World world : Bukkit.getWorlds()) {
            if (world != null) {
                world.setGameRule(gameRule, value);
            }
        }

    }

    public static void playSoundForAll(Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public static List<UUID> getPermitted() {
        List<UUID> uuids = new ArrayList<UUID>();
        for (Map.Entry<UUID, PermissionAttachment> entry : CoreMain.permissionAttachmentHashMap.entrySet()) {
            uuids.add(entry.getKey());
        }
        return uuids;
    }

    public static void createHealthDisplay(boolean enabled) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        if (enabled) {
            Objective objective = scoreboard.registerNewObjective("Leben", Criterias.HEALTH, "Leben");
            objective.setRenderType(RenderType.HEARTS);
            objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setScoreboard(scoreboard);
                player.setHealth(1);
                heal(player);
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
            }
        }
    }

    public static void addServerInfo(String serverName, double tps) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Date date = new Date();
            String dateFormatted = new SimpleDateFormat("HH:mm").format(date);

            List<String> headerList = new ArrayList<String>();
            headerList.add(Utils.colorize("&0--&8---&7---&f[&eP&aM&bF&9R&dT&cT&f-Server-Network]&7---&8---&0--&f"));
            headerList.add("");
            headerList.add(Utils.colorize("Moin &b" + player.getDisplayName() + "&f!"));
            headerList.add(Utils.colorize("Es ist &b" + dateFormatted));
            headerList.add(Utils.colorize("&fDu befindest dich auf &b" + serverName));
            headerList.add("   ");


            List<String> footerList = new ArrayList<String>();
            footerList.add("   ");
            footerList.add(Utils.colorize("&8Server-Software: &e" + Bukkit.getServer().getVersion()));
            footerList.add(Utils.colorize("&8Server-TPS: &e" + new DecimalFormat("#.#").format(tps) + "&8 Ticks per second"));
            footerList.add(Utils.colorize("&7" + Bukkit.getIp() + "&f:&7" + Bukkit.getServer().getPort() + " (&e" + getPlayerPing(player) + "&7ms)"));

            String header = StringUtils.join(headerList, "\n");
            String footer = StringUtils.join(footerList, "\n");

            player.setPlayerListHeaderFooter(header, footer);
        }
    }

    public static int getPlayerPing(Player player) {
        CraftPlayer pingablePlayer = (CraftPlayer) player;
        return pingablePlayer.getHandle().ping;
    }

    public static boolean isPlayer(UUID uuid) {
        return getPlayer(uuid) != null;
    }

    public static boolean isPlayer(String name) {
        return getPlayer(name) != null;
    }

    public static String getDisplayName(Player player) {
        return player.getDisplayName();
    }

    public static String getDisplayName(String name) {
        Player player = getPlayer(name);
        return player.getDisplayName();
    }

    public static String getDisplayName(UUID uuid) {
        Player player = getPlayer(uuid);
        return player.getDisplayName();
    }

    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

}
