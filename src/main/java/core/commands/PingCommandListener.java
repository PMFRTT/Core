package core.commands;

import core.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }


        if (command.getLabel().equalsIgnoreCase("ping")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.getPrefix("Server") + Utils.colorize("Dein Ping ist: &e" + Utils.getPlayerPing(player) + "&fms"));
                return true;
            } else if (args.length == 1) {
                if (Utils.isPlayer(args[0])) {
                    sender.sendMessage(Utils.getPrefix("Server") + Utils.colorize("Der Ping von &a" + Bukkit.getPlayer(args[0]).getDisplayName() + "&f ist &e" + Utils.getPlayerPing(Bukkit.getPlayer(args[0])) + "&fms"));
                    return true;
                } else {
                    sender.sendMessage(Utils.getPrefix("Server") + Utils.colorize("Der Spieler &c" + args[0] + " &fist nicht Online!"));
                    return false;
                }
            }

        }
        return false;
    }
}
