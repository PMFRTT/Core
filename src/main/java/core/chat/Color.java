package core.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Color {
    RED, GREEN, BLUE;

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String colorizeHex(String message) {
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher matcher = pattern.matcher(message); // Creates a matcher with the given pattern & message
            while (matcher.find()) { // Searches the message for something that matches the pattern
                String color = message.substring(matcher.start(), matcher.end()); // Extracts the color from the message
                message = message.replace(color, "" + net.md_5.bungee.api.ChatColor.of(color)); // Places the color in the message
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
