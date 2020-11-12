package core.register;

import core.Utils;
import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommandListener implements CommandExecutor {

    CoreMain main;

    public RegisterCommandListener(CoreMain main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }


        if (command.getLabel().equals("register")) {
            if (args.length == 1) {
                RegisterMain.registerPlayer(player, args[0]);
            }else if(args.length == 2){
                if(Bukkit.getPlayer(args[1]) != null){
                    RegisterMain.registerPlayer(Bukkit.getPlayer(args[1]), args[0]);
                }
            }else{
                player.sendMessage(Utils.getPrefix("Registrierung") + Utils.colorize("Verwende &6/register <code>&f um dich zu registrieren!"));
            }
        }


        return false;
    }
}
