package core.commands;

import core.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BugCommandExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (command.getLabel().equalsIgnoreCase("bug")) {
            if (args.length == 0) {
                assert player != null;
                player.sendMessage(Utils.getPrefix("Bug") + Utils.colorize("Bitte wähle aus den installierten Plugins. Wenn du unsicher bist, verwende &e/bug core&f!"));
                player.sendMessage(Utils.getPrefix("Bug") + Utils.colorize("/bug < core | bingo | lobby | positions >"));
            } else if (args.length == 1) {
                assert player != null;
                switch (args[0]) {
                    case "core" -> {
                        TextComponent textComponent = new TextComponent(Utils.getPrefix("Bug") + Utils.colorize("Klicke hier um einen Fehler zu melden!"));
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/PMFRTT/Core/issues/new"));
                        player.spigot().sendMessage(textComponent);
                    }
                    case "bingo" -> {
                        TextComponent textComponent = new TextComponent(Utils.getPrefix("Bug") + Utils.colorize("Klicke hier um einen Fehler zu melden!"));
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/PMFRTT/Bingo/issues/new"));
                        player.spigot().sendMessage(textComponent);
                    }
                    case "lobby" -> {
                        TextComponent textComponent = new TextComponent(Utils.getPrefix("Bug") + Utils.colorize("Klicke hier um einen Fehler zu melden!"));
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/PMFRTT/Lobby/issues/new"));
                        player.spigot().sendMessage(textComponent);
                    }
                    case "positions" -> {
                        TextComponent textComponent = new TextComponent(Utils.getPrefix("Bug") + Utils.colorize("Klicke hier um einen Fehler zu melden!"));
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/PMFRTT/positions/issues/new"));
                        player.spigot().sendMessage(textComponent);
                    }
                }
            }
        }
        return false;
    }
}
