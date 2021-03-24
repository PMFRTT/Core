package core.commands;

import core.Utils;
import core.core.CoreMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VersionCommandListener implements CommandExecutor {

    private final CoreMain main;

    public VersionCommandListener(CoreMain main){
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }

        if (command.getLabel().equalsIgnoreCase("core")) {
            player.sendMessage(Utils.getPrefix("Core") + Utils.colorize("Du Verwendest &2CorePlugin (" + main.getDescription().getVersion() + ") &fDie neueste Version ist &2" + main.sqlConfig.getConfigbyName("version")));
        }
        return false;
    }
}
