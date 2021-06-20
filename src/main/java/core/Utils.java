package core;

import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import core.settings.Setting.Setting;
import core.settings.Setting.SettingsType;
import core.settings.Settings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    private static boolean lessthan40 = false;
    private static boolean lessthan20 = false;

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

    public static String colorize(String message) {
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
        StringBuilder coloredMessage = new StringBuilder();
        Random random = new Random();
        for (Character c : message.toCharArray()) {
            coloredMessage.append(rainbowStrings[random.nextInt(rainbowStrings.length)]).append(c);
        }
        return coloredMessage.toString();
    }

    public static boolean convertToBoolean(int i) {
        return i == 1;
    }

    public static Integer getSettingValueInt(Settings settings, String name) {
        Setting setting = settings.getSettingbyName(name);
        assert setting != null;
        if (setting.getType().equals(SettingsType.CYCLE)) {
            return (Integer) setting.getValue();
        }
        return null;
    }

    public static Boolean getSettingValueBool(Settings settings, String name) {
        Setting setting = settings.getSettingbyName(name);
        assert setting != null;
        if (setting.getType().equals(SettingsType.SWITCH)) {
            return (Boolean) setting.getValue();
        }
        return null;
    }

    public static void heal(Player player) {
        DebugSender.sendDebug(DebugType.PLAYER, "player " + player.getDisplayName() + " has been healed");
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
        DebugSender.sendDebug(DebugType.SERVER, "gamerule " + gameRule.getName() + " has been set to " + value);
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
            footerList.add("   ");
            if (CoreMain.rankUpdater.isDev(player)) {
                if (CoreMain.sqlConfig.getConfigbyName("debug").equals("1") || CoreMain.sqlConfig.getConfigbyName("debug").equals("3")) {
                    footerList.add(Utils.colorize("&8Server-Software: &e" + Bukkit.getServer().getVersion()));
                    footerList.add(Utils.colorize("&8Server-TPS: &e" + new DecimalFormat("#.#").format(tps) + "&8 Ticks per second"));
                    footerList.add(Utils.colorize("&8" + Bukkit.getIp() + ":" + Bukkit.getServer().getPort() + " (&e" + getPlayerPing(player) + "&8ms)"));
                    footerList.add(Utils.colorize("&8Verwendeter Speicher: &e" + formatToMB(getUsedMemory()) + "MiB&8/&e" + formatToMB(getTotalMemory()) + "MiB " + getMemoryUsage()));
                    if (CoreMain.SQL.isConnected()) {
                        footerList.add(Utils.colorize("&8" + "Datenbank ist &averbunden"));
                    } else {
                        footerList.add(Utils.colorize("&8" + "Datenbank ist &cgetrennt"));
                    }
                }
            }

            String header = StringUtils.join(headerList, "\n");
            String footer = StringUtils.join(footerList, "\n");

            player.setPlayerListHeaderFooter(header, footer);
        }
    }

    public static int getPlayerPing(Player player) {
        CraftPlayer pingablePlayer = (CraftPlayer) player;
        return pingablePlayer.getPing();
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

    public static String getMemoryUsage() {
        return "(" + new DecimalFormat("#.000").format(((float) getUsedMemory() / (float) getTotalMemory()) * 100) + "%)";
    }

    public static long formatToMB(long bytes) {
        return bytes / 1048576;
    }

    public static void checkMemory() {

        if (((float) getFreeMemory() / (float) getTotalMemory()) < 0.2 && !lessthan40) {
            DebugSender.sendDebug(DebugType.SERVER, "free memory below 20%");
            lessthan40 = true;
        } else if (((float) getFreeMemory() / (float) getTotalMemory()) < 0.1 && !lessthan20) {
            DebugSender.sendDebug(DebugType.SERVER, "free memory below &c10%");
            lessthan20 = true;
        } else if (((float) getFreeMemory() / (float) getTotalMemory()) > 0.2 && lessthan20) {
            lessthan20 = false;
        } else if (((float) getFreeMemory() / (float) getTotalMemory()) > 0.3 && lessthan40) {
            DebugSender.sendDebug(DebugType.SERVER, "memory freed");
            lessthan40 = false;
            lessthan20 = false;
        }
    }

}
