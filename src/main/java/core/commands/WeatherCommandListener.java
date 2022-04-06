package core.commands;

import core.Utils;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WeatherCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }

        World world = Bukkit.getWorld("world");

        if (command.getLabel().equalsIgnoreCase("weather")) {
            if (PermissionConverter.generatePermissions(Objects.requireNonNull(player)).get(Permission.WEATHER) || sender.getName().equalsIgnoreCase("console")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("r")) {
                        Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Wetter wurde auf &bRegen&f geändert!"));
                        assert world != null;
                        world.setStorm(true);
                        return true;
                    } else if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")) {
                        Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Wetter wurde auf &6Sonne&f geändert!"));
                        assert world != null;
                        world.setStorm(false);
                        return true;
                    } else if (args[0].equalsIgnoreCase("thunder") || args[0].equalsIgnoreCase("t")) {
                        Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Wetter wurde auf &8Gewitter&f geändert!"));
                        assert world != null;
                        world.setStorm(true);
                        world.setThundering(true);
                        return true;
                    } else {
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/weather <rain, clear, thunder>&f um das Wetter zu ändern!"));
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/weather <rain, clear, thunder>&f um das Wetter zu ändern!"));
                    return true;
                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        }
        return false;
    }
}
