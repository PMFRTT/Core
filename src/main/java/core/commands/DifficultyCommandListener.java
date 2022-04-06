package core.commands;

import core.Utils;
import core.debug.DebugSender;
import core.debug.DebugType;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DifficultyCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        World world = Bukkit.getWorld("world");

        if (command.getLabel().equalsIgnoreCase("difficulty")) {
            if (PermissionConverter.generatePermissions(Objects.requireNonNull(player)).get(Permission.DIFFICULTY) || sender.getName().equalsIgnoreCase("console")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("peaceful")) {
                        Objects.requireNonNull(world).setDifficulty(Difficulty.PEACEFUL);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&a PEACEFUL&f!"));
                        DebugSender.sendDebug(DebugType.SERVER, "difficulty changed");
                        return true;
                    } else if (args[0].equalsIgnoreCase("easy")) {
                        Objects.requireNonNull(world).setDifficulty(Difficulty.EASY);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&a EINFACH&f!"));
                        DebugSender.sendDebug(DebugType.SERVER, "difficulty changed");
                        return true;
                    } else if (args[0].equalsIgnoreCase("normal")) {
                        Objects.requireNonNull(world).setDifficulty(Difficulty.NORMAL);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&e NORMAL&f!"));
                        DebugSender.sendDebug(DebugType.SERVER, "difficulty changed");
                        return true;
                    } else if (args[0].equalsIgnoreCase("hard")) {
                        Objects.requireNonNull(world).setDifficulty(Difficulty.HARD);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&c SCHWER&f!"));
                        DebugSender.sendDebug(DebugType.SERVER, "difficulty changed");
                        return true;
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/difficulty <peaceful, easy, normal, hard>&f um die Schwierigkeit zu ändern!"));
                        return false;
                    }
                } else {
                    sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/difficulty <peaceful, easy, normal, hard>&f um die Schwierigkeit zu ändern!"));
                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }

        }

        return false;
    }
}
