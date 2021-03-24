package core.commands;

import core.Utils;
import core.permissions.Permission;
import core.permissions.PermissionConverter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }
        if (command.getLabel().equalsIgnoreCase("gamemode")) {
            if (PermissionConverter.generatePermissions(player).get(Permission.GAMEMODE) || sender.getName().equalsIgnoreCase("console")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                        if (args.length == 2) {
                            if (args[1] != null && Bukkit.getPlayer(args[1]) != null) {
                                player = Bukkit.getPlayer(args[1]);
                                if (player.getGameMode() != GameMode.CREATIVE) {
                                    player.setGameMode(GameMode.CREATIVE);
                                    Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde von " + Utils.colorize("&1" + sender.getName() + "&f") + " auf " + Utils.colorize("&bCreative") + Utils.colorize("&f geändert!"));
                                    return true;
                                }
                            }
                        } else {
                            if (player.getGameMode() != GameMode.CREATIVE) {
                                player.setGameMode(GameMode.CREATIVE);
                                Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde auf " + Utils.colorize("&bCreative") + Utils.colorize("&f geändert!"));
                                return true;
                            } else {
                                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&4Du bist bereits im Gamemode &bCreative!"));
                                return false;
                            }

                        }
                    } else if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                        if (args.length == 2) {
                            if (args[1] != null && Bukkit.getPlayer(args[1]) != null) {
                                player = Bukkit.getPlayer(args[1]);
                                if (player.getGameMode() != GameMode.SURVIVAL) {
                                    player.setGameMode(GameMode.SURVIVAL);
                                    Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde von " + Utils.colorize("&1" + sender.getName() + "&f") + " auf " + Utils.colorize("&bSurvival") + Utils.colorize("&f geändert!"));
                                    return true;
                                }

                            }
                        } else {

                            if (player.getGameMode() != GameMode.SURVIVAL) {
                                player.setGameMode(GameMode.SURVIVAL);
                                Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde auf " + Utils.colorize("&bSurvival") + Utils.colorize("&f geändert!"));
                                return true;
                            } else {
                                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&4Du bist bereits im Gamemode &bSurvial!"));
                                return false;
                            }

                        }
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("2")) {
                        if (args.length == 2) {
                            if (args[1] != null && Bukkit.getPlayer(args[1]) != null) {
                                player = Bukkit.getPlayer(args[1]);
                                if (player.getGameMode() != GameMode.SPECTATOR) {
                                    player.setGameMode(GameMode.SPECTATOR);
                                    Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde von " + Utils.colorize("&1" + sender.getName() + "&f") + " auf " + Utils.colorize("&bSpectator") + Utils.colorize("&f geändert!"));
                                    return true;
                                }
                            }
                        } else {
                            if (player.getGameMode() != GameMode.SPECTATOR) {
                                player.setGameMode(GameMode.SPECTATOR);
                                Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde auf " + Utils.colorize("&bSpectator") + Utils.colorize("&f geändert!"));
                                return true;
                            } else {
                                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&4Du bist bereits im Gamemode &bSpectator!"));
                                return false;
                            }

                        }
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("3")) {
                        if (args.length == 2) {
                            if (args[1] != null && Bukkit.getPlayer(args[1]) != null) {
                                player = Bukkit.getPlayer(args[1]);
                                if (player.getGameMode() != GameMode.ADVENTURE) {
                                    player.setGameMode(GameMode.ADVENTURE);
                                    Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde von " + Utils.colorize("&1" + sender.getName() + "&f") + " auf " + Utils.colorize("&bAdventure") + Utils.colorize("&f geändert!"));
                                    return true;
                                }

                            }
                        } else {
                            if (player.getGameMode() != GameMode.ADVENTURE) {
                                player.setGameMode(GameMode.ADVENTURE);
                                Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Gamemode von " + Utils.colorize("&2" + player.getDisplayName() + "&f")) + " wurde auf " + Utils.colorize("&bAdventure") + Utils.colorize("&f geändert!"));
                                return true;
                            } else {
                                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&4Du bist bereits im Gamemode &bAdventure!"));
                                return false;
                            }

                        }
                    } else {
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/gamemode <s, c, sp, a>&f um deinen Gamemode zu ändern!"));
                        return true;
                    }

                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        }
        return false;
    }
}
