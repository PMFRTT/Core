package core.commands;

import core.chat.Color;
import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Objects;

public class ScheduleCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("schedule")) {
            if (Bukkit.getPlayer(args[0]) != null) {
                if (args[1].equalsIgnoreCase("hotbar")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String string : Arrays.asList(args).subList(2, args.length)) {
                        stringBuilder.append(" ").append(string);
                    }
                    CoreMain.hotbarManager.getHotbarScheduler(Objects.requireNonNull(Bukkit.getPlayer(args[0]))).scheduleMessage(Color.colorizeHex(stringBuilder.toString()), Integer.parseInt(args[2]));
                }
            }
        }
        return false;
    }
}
