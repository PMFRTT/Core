package core.commands;

import core.Utils;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InvseeCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }

        if (command.getLabel().equalsIgnoreCase("invsee")) {
            if (PermissionConverter.generatePermissions(Objects.requireNonNull(player)).get(Permission.INVENTORY)) {
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        player.openInventory(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getInventory());

                        return true;
                    }
                } else {
                    sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/invsee <Name>&f um das Inventar eines anderen Spielers zu sehen!"));
                }
            } else {
                sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        }

        return false;
    }
}
