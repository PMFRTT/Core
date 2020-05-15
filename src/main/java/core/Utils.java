package core;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scoreboard.*;

import java.text.DecimalFormat;
import java.util.*;

public class Utils {

    private static final String[] rainbowStrings = {
            "&c", "&6", "&e", "&a", "&9", "&d", "&5"
    };


    public static String getPrefix(String pluginName) {
        return colorize("[&2" + pluginName + "&f]: ");
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

        player.setHealth(20);
        player.setFoodLevel(20);


        /**BukkitTask runnable = new BukkitRunnable() {
        @Override public void run() {
        if (player.getHealth() < 20) {
        if (player.getHealth() + 0.5 > 20.0) {
        player.setHealth(20);
        } else {
        player.setHealth(player.getHealth() + 0.5);
        }
        }
        if (player.getFoodLevel() > 20) {
        if (player.getFoodLevel() + 0.5 > 20) {
        player.setFoodLevel(20);
        } else {
        player.setFoodLevel(player.getFoodLevel() + 1);ffggg
        }
        }

        if (player.getFoodLevel() >= 20 && player.getHealth() >= 20) {
        cancel();
        }
        }

        }.runTaskTimer(pluginMain, 0L, 1L);*/
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

    public static int getPing(Player player) {
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) player;
        int ping = proxiedPlayer.getPing();
        return ping;
    }

    public static void changeGamerule(GameRule<Boolean> gameRule, boolean value) {
        List<World> dimensions = new ArrayList<World>();

        dimensions.add(Bukkit.getWorld("world"));
        dimensions.add(Bukkit.getWorld("world_nether"));
        dimensions.add(Bukkit.getWorld("world_the_end"));

        for (World world : dimensions) {
            if (world != null) {
                world.setGameRule(gameRule, value);
            }
        }

    }

    public static List<UUID> getPermitted() {
        List<UUID> uuids = new ArrayList<UUID>();
        for (Map.Entry<UUID, PermissionAttachment> entry : CoreMain.permissionAttachmentHashMap.entrySet()) {
            uuids.add(entry.getKey());
        }
        return uuids;
    }

    public static void createHealthDisplay() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Leben", Criterias.HEALTH, "Leben");
        objective.setRenderType(RenderType.HEARTS);
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }

}
