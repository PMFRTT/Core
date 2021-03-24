package core.commands;

import core.Utils;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommandListener implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (command.getLabel().equalsIgnoreCase("heal")) {
            if (PermissionConverter.generatePermissions(player).get(Permission.HEAL) || sender.getName().equals("CONSOLE")) {
                if (args.length == 0) {
                    if (sender.getName() != "CONSOLE") {
                        Player p = (Player) sender;
                        Utils.heal(p);
                        p.sendMessage(Utils.getServerPrefix() + Utils.colorize("Du wurdest von &b" + sender.getName() + "&f geheilt"));
                        return true;
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDie Console kann nicht geheilt werden!"));
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("all")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Utils.heal(p);
                        p.sendMessage(Utils.getServerPrefix() + Utils.colorize("Du wurdest von &b" + sender.getName() + "&f geheilt"));
                        return true;
                    }
                } else if (Bukkit.getPlayer(args[0]) != null) {
                    Player toHeal = Bukkit.getPlayer(args[0]);
                    Utils.heal(toHeal);
                    toHeal.sendMessage(Utils.getServerPrefix() + Utils.colorize("Du wurdest von &b" + sender.getName() + "&f geheilt"));
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
