package core.commands;

import core.Utils;
import core.debug.DebugSender;
import core.debug.DebugType;
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

public class TimeCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player player = null;
        if(sender instanceof  Player){
            player = (Player) sender;
        }

        World world = Bukkit.getWorld("world");

        if (command.getLabel().equalsIgnoreCase("time")) {
            if (args.length == 2) {
                if (PermissionConverter.generatePermissions(Objects.requireNonNull(player)).get(Permission.TIME) || sender.getName().equalsIgnoreCase("console")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("morning")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &6Morgen!"));
                            Objects.requireNonNull(world).setTime(0);
                            DebugSender.sendDebug(DebugType.SERVER, "time was changed");
                            return true;
                        } else if (args[1].equalsIgnoreCase("day")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &bMittag!"));
                            Objects.requireNonNull(world).setTime(6000);
                            DebugSender.sendDebug(DebugType.SERVER, "time was changed");
                            return true;
                        } else if (args[1].equalsIgnoreCase("evening")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &9Abend!"));
                            Objects.requireNonNull(world).setTime(12000);
                            DebugSender.sendDebug(DebugType.SERVER, "time was changed");
                            return true;
                        } else if (args[1].equalsIgnoreCase("night")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &8Nacht!"));
                            Objects.requireNonNull(world).setTime(18000);
                            DebugSender.sendDebug(DebugType.SERVER, "time was changed");
                            return true;
                        } else {
                            try {
                                long setTimeTo = Long.parseLong(args[1]);
                                Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Die Zeit ist jetzt &2" + Utils.formatIngameTime(setTimeTo) + "&f!"));
                                Objects.requireNonNull(world).setTime(setTimeTo);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            DebugSender.sendDebug(DebugType.SERVER, "time was changed");
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("add")) {
                        try {
                            int toAdd = Integer.parseInt(args[1]);
                            long currentTime = Objects.requireNonNull(world).getTime();
                            long setTimeTo = currentTime + toAdd;
                            world.setTime(setTimeTo);
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Zeit ist jetzt &2" + Utils.formatIngameTime(setTimeTo) + "&f!"));
                        } catch (NumberFormatException e) {
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&c" + args[1] + "ist keine Zahl!"));
                        }
                        DebugSender.sendDebug(DebugType.SERVER, "time was changed");
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                    return false;
                }
            } else if (args.length == 0) {
                Objects.requireNonNull(player).sendMessage(Utils.getServerPrefix() + Utils.colorize("Es ist gerade &2" + Utils.formatIngameTime(Objects.requireNonNull(world).getTime()) + "&f Uhr!"));
            }
        }
        return false;
    }
}
