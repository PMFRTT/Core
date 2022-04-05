package core.commands;

import core.Utils;
import core.core.CoreHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SQLCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (command.getLabel().equalsIgnoreCase("sql")) {
            assert player != null;
            if (player.isOp() || sender.getName().equalsIgnoreCase("CONSOLE")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("connect")) {
                        try {
                            boolean success = CoreHandler.getSQL().connect();
                            if(success){
                                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Verbindung zur Datenbank war &aerfolgreich"));
                            }else{
                                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Verbindung zur Datenbank &bbesteht bereits"));
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("disconnect")) {
                        try {
                            CoreHandler.getSQL().disconnect();
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Verbindung zur Datenbank wurde &cgetrennt"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("reconnect")) {
                        try {
                            CoreHandler.getSQL().disconnect();
                            CoreHandler.getSQL().connect();
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Verbindung zur Datenbank wurde &aneu aufgebaut"));
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDer Befehl, den du eingegeben hast, existiert nicht!"));
                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast keine Rechte auf diesen Befehl!"));
            }
        }

        return false;
    }
}
