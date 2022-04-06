package core.commands;

import core.bungee.CoreBungeeCordClient;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HubCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (command.getLabel().equalsIgnoreCase("hub") || command.getLabel().equalsIgnoreCase("lobby") || command.getLabel().equalsIgnoreCase("l")) {
            assert player != null;
            CoreBungeeCordClient.moveToServer(player, "LobbyServer");
            return true;
        }

        return false;
    }
}
