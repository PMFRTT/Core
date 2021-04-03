package core;

import core.core.CoreMain;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static CoreMain corePlugin;

    public Utils(CoreMain corePlugin) {
        Utils.corePlugin = corePlugin;
    }

    private static final Pattern pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");

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

    public static String getDebugPrefix() {
        return colorize("[&cDebug&f]: ");
    }

    public static void sendDebugMessage(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (CoreMain.mySQLRanks.getRank(player.getUniqueId()) > 2) {
                player.sendMessage(Utils.getDebugPrefix() + Utils.colorize(msg));
            }
        }
    }

    public static String getChatPrefix(Player player) {
        if (player.hasPermission("core.canShowTPS")) {
            return colorize("[&c" + player.getDisplayName() + "&f]: ");
        } else {
            return colorize("[&b" + player.getDisplayName() + "&f]: ");
        }
    }

    public static String getJoinPrefix(String pluginName, Player player) {
        sendDebugMessage("client connected to server");
        return getPrefix(pluginName) + colorize("&a" + player.getDisplayName() + " &fhat den Server betreten!");
    }

    public static String getDisconnectPrefix(String pluginName, Player player) {
        sendDebugMessage("client disconnected from server");
        return getPrefix(pluginName) + colorize("&c" + player.getDisplayName() + " &fhat den Server verlassen!");
    }

    public static String getServerPrefix() {
        return "[" + colorize("&2Server") + colorize("&f]: ");
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String colorizeHex(String message) {
        Matcher matcher = pattern.matcher(message); // Creates a matcher with the given pattern & message
        while (matcher.find()) { // Searches the message for something that matches the pattern
            String color = message.substring(matcher.start(), matcher.end()); // Extracts the color from the message
            message = message.replace(color, "" + net.md_5.bungee.api.ChatColor.of(color)); // Places the color in the message
        }

        return message;
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

    public static String formatTimerTimeText(int seconds) {
        String time;
        DecimalFormat decimalFormat = new DecimalFormat("0");
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        String secondsFormatted = decimalFormat.format(remainingSeconds);
        String minutesFormatted = decimalFormat.format(minutes);
        String hoursFormatted = decimalFormat.format(hours);
        StringBuilder stringBuilder = new StringBuilder();
        if (hours != 0) {
            stringBuilder.append(hoursFormatted).append(" Stunden und ");
        }
        if (minutes != 0) {
            stringBuilder.append(minutesFormatted).append(" Minuten und ");
        }
        if (remainingSeconds != 0) {
            stringBuilder.append(secondsFormatted).append(" Sekunden");
        }
        return stringBuilder.toString();
    }

    public static String formatTimerTimeTicks(int ticks) {
        String time;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        DecimalFormat decimalFormatticks = new DecimalFormat(".0");
        int hours = ticks / 72000;
        int minutes = (ticks % 72000) / 1200;
        int seconds = ((ticks % 72000) % 1200) / 20;
        float remainingticks = ((((float) ticks % 72000) % 1200) % 20) / 20;
        String ticksFormatted = decimalFormatticks.format(remainingticks);
        String secondsFormatted = decimalFormat.format(seconds);
        String minutesFormatted = decimalFormat.format(minutes);
        String hoursFormatted = decimalFormat.format(hours);
        time = hoursFormatted + ":" + minutesFormatted + ":" + secondsFormatted + ticksFormatted;
        return time;
    }

    public static String formatTimerTimeTicksTwoDecimal(int ticks) {
        String time;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        DecimalFormat decimalFormatticks = new DecimalFormat(".00");
        int hours = ticks / 72000;
        int minutes = (ticks % 72000) / 1200;
        int seconds = ((ticks % 72000) % 1200) / 20;
        float remainingticks = ((((float) ticks % 72000) % 1200) % 20) / 20;
        String ticksFormatted = decimalFormatticks.format(remainingticks);
        String secondsFormatted = decimalFormat.format(seconds);
        String minutesFormatted = decimalFormat.format(minutes);
        String hoursFormatted = decimalFormat.format(hours);
        time = hoursFormatted + ":" + minutesFormatted + ":" + secondsFormatted + ticksFormatted;
        return time;
    }

    public static String formatTimerTimeTicksThreeDecimal(int ticks) {
        String time;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        DecimalFormat decimalFormatticks = new DecimalFormat(".000");
        int hours = ticks / 72000;
        int minutes = (ticks % 72000) / 1200;
        int seconds = ((ticks % 72000) % 1200) / 20;
        float remainingticks = ((((float) ticks % 72000) % 1200) % 20) / 20;
        String ticksFormatted = decimalFormatticks.format(remainingticks);
        String secondsFormatted = decimalFormat.format(seconds);
        String minutesFormatted = decimalFormat.format(minutes);
        String hoursFormatted = decimalFormat.format(hours);
        time = hoursFormatted + ":" + minutesFormatted + ":" + secondsFormatted + ticksFormatted;
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
        sendDebugMessage(player.getDisplayName() + "has been healed");
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
        sendDebugMessage(gameRule.getName() + " has been set to " + value);
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
            String dateFormatted = new SimpleDateFormat("HH:mm:ss").format(date);

            List<String> headerList = new ArrayList<String>();
            headerList.add(Utils.colorize("&0--&8---&7---&f[&eP&aM&bF&9R&dT&cT&f-Server-Network]&7---&8---&0--&f"));
            headerList.add("");
            headerList.add(Utils.colorize("Moin &b" + player.getDisplayName() + "&f!"));
            headerList.add(Utils.colorize("Es ist &b" + dateFormatted));
            headerList.add(Utils.colorize("&fDu befindest dich auf &b" + serverName));
            headerList.add("   ");


            List<String> footerList = new ArrayList<String>();
            if (CoreMain.mySQLRanks.getRank(player.getUniqueId()) == 4) {
                footerList.add("   ");
                footerList.add(Utils.colorize("&8Server-Software: &e" + Bukkit.getServer().getVersion()));
                footerList.add(Utils.colorize("&8Server-TPS: &e" + new DecimalFormat("#.#").format(tps) + "&8 Ticks per second"));
                footerList.add(Utils.colorize("&8" + Bukkit.getIp() + ":" + Bukkit.getServer().getPort() + " (&e" + getPlayerPing(player) + "&8ms)"));
                footerList.add(Utils.colorize("&8Verwendeter Speicher: &e" + formatToMB(getUsedMemory()) + "&8/&e" + formatToMB(getTotalMemory())));
                if (CoreMain.SQL.isConnected()) {
                    footerList.add(Utils.colorize("&8" + "Datenbank ist &averbunden"));
                } else {
                    footerList.add(Utils.colorize("&8" + "Datenbank ist &cgetrennt"));
                }
            }
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

    public static long getUsedMemory() {
        return getTotalMemory() - getFreeMemory();
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long formatToMB(long bytes) {
        return bytes / 1048576;
    }

}
