package core.commands;

import core.Utils;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DifficultyCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }

        World world = Bukkit.getWorld("world");

        if (command.getLabel().equalsIgnoreCase("difficulty")) {
            if (PermissionConverter.generatePermissions(player).get(Permission.DIFFICULTY) || sender.getName().equalsIgnoreCase("console")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("peaceful")) {
                        world.setDifficulty(Difficulty.PEACEFUL);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&a PEACEFUL&f!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("easy")) {
                        world.setDifficulty(Difficulty.EASY);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&a EINFACH&f!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("normal")) {
                        world.setDifficulty(Difficulty.NORMAL);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&e NORMAL&f!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("hard")) {
                        world.setDifficulty(Difficulty.HARD);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&c SCHWER&f!"));
                        return true;
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/difficulty <peaceful, easy, normal, hard>&f um die Schwierigkeit zu ändern!"));
                        return false;
                    }
                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }

        }

        return false;
    }
}
