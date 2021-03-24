package core.commands;

import core.Utils;
import core.core.CoreSendStringPacket;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TeleportCommandListener implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if(sender instanceof  Player){
            player = (Player) sender;
        }

        if (command.getLabel().equalsIgnoreCase("teleport")) {
            if (PermissionConverter.generatePermissions(player).get(Permission.TELEPORT)) {
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("Teleportiere zu"), Utils.colorize("&a" + Bukkit.getPlayer(args[0]).getDisplayName()));
                        player.teleport(Objects.requireNonNull(Bukkit.getPlayer(args[0])));
                    }
                } else if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[1]) != null) {
                        CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("Du wurdest zu"), Utils.colorize("&a" + Bukkit.getPlayer(args[1]).getDisplayName() + " teleportiert"));
                        Bukkit.getPlayer(args[0]).teleport(Bukkit.getPlayer(args[1]).getLocation());
                    }

                } else if (args.length == 3) {
                    CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("Teleportiere zu"), Utils.colorize("&fX:&a" + Integer.parseInt(args[0]) + "&f Y:&a" + Integer.parseInt(args[1]) + "&f Z:&a" + Integer.parseInt(args[2])));
                    player.teleport(new Location(player.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                } else {
                    sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/tp; /teleport <Spieler>; <X> <Y> <Z>&f um dein Leben zu ändern :)!"));
                }
            } else {
                sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));

            }
        }

        return false;
    }

}
