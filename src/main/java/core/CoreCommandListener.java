package core;

import net.minecraft.server.v1_15_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CoreCommandListener implements CommandExecutor {

    CoreMain corePlugin;
    Collection<? extends Player> players = Bukkit.getOnlinePlayers();

    public CoreCommandListener(CoreMain corePlugin) {
        this.corePlugin = corePlugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        World world = Bukkit.getWorld("world");


        if (command.getLabel().equalsIgnoreCase("gamemode")) {
            if (sender.hasPermission("core.canChangeGamemode") || sender.getName().equalsIgnoreCase("console")) {
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
        } else if (command.getLabel().equalsIgnoreCase("weather")) {
            if (sender.hasPermission("core.canChangeWeather") || sender.getName().equalsIgnoreCase("console")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("r")) {
                        Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Wetter wurde auf &bRegen&f geändert!"));
                        world.setStorm(true);
                        return true;
                    } else if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")) {
                        Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Wetter wurde auf &6Sonne&f geändert!"));
                        world.setStorm(false);
                        return true;
                    } else if (args[0].equalsIgnoreCase("thunder") || args[0].equalsIgnoreCase("t")) {
                        Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Wetter wurde auf &8Gewitter&f geändert!"));
                        world.setStorm(true);
                        world.setThundering(true);
                        return true;
                    } else {
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/weather <rain, clear, thunder>&f um das Wetter zu ändern!"));
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/weather <rain, clear, thunder>&f um das Wetter zu ändern!"));
                    return true;
                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        } else if (command.getLabel().equalsIgnoreCase("time")) {
            if (args.length == 2) {
                if (sender.hasPermission("core.canChangeTime") || sender.getName().equalsIgnoreCase("console")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("morning")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &6Morgen!"));
                            world.setTime(0);
                            return true;
                        } else if (args[1].equalsIgnoreCase("day")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &bMittag!"));
                            world.setTime(6000);
                            return true;
                        } else if (args[1].equalsIgnoreCase("evening")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &9Abend!"));
                            world.setTime(12000);
                            return true;
                        } else if (args[1].equalsIgnoreCase("night")) {
                            Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Es ist jetzt &8Nacht!"));
                            world.setTime(18000);
                            return true;
                        } else if (args[1] != null || args[1] == "") {
                            try {
                                Long setTimeTo = Long.parseLong(args[1]);
                                Utils.sendMessageToEveryone(Utils.getServerPrefix() + Utils.colorize("Die Zeit ist jetzt &2" + Utils.formatIngameTime(setTimeTo) + "&f!"));
                                world.setTime(setTimeTo);
                            } catch (NumberFormatException e) {
                            }
                            return true;
                        } else {
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/time set <morning, day, evening, night>&f um die Zeit zu ändern!"));
                        }
                    } else if (args[0].equalsIgnoreCase("add")) {
                        try {
                            int toAdd = Integer.parseInt(args[1]);
                            long currentTime = world.getTime();
                            long setTimeTo = currentTime + toAdd;
                            world.setTime(setTimeTo);
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Zeit ist jetzt &2" + Utils.formatIngameTime(setTimeTo) + "&f!"));
                        } catch (NumberFormatException e) {
                            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&c" + args[1] + "ist keine Zahl!"));
                        }
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                    return false;
                }
            } else if (args.length == 0) {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("Es ist gerade &2" + Utils.formatIngameTime(world.getTime()) + "&f Uhr!"));
            }
        } else if (command.getLabel().equalsIgnoreCase("core")) {
            player.sendMessage(Utils.getPrefix("Core") + Utils.colorize("Du Verwendest &2CorePlugin (" + corePlugin.getDescription().getVersion() + ") &fDie neueste Version ist &2v0.0.1&f!"));
        }

         else if (command.getLabel().equalsIgnoreCase("hub") || command.getLabel().equalsIgnoreCase("lobby") || command.getLabel().equalsIgnoreCase("l")) {
            assert player != null;
            CoreBungeeCordClient.moveToServer(player, "Lobby");
            return true;


        } else if (command.getLabel().equalsIgnoreCase("heal")) {
            if (sender.hasPermission("core.canHeal") || sender.getName().equals("CONSOLE")) {
                if (args.length == 0) {
                    if (sender.getName() != "CONSOLE") {
                        Player p = (Player) sender;
                        Utils.heal(p);
                        p.sendMessage(Utils.getServerPrefix() + Utils.colorize("Du wurdest von &b" + sender.getName() + "&f geheilt"));
                        return true;
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDie Console kann nicht geheilt werden!"));
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("all")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Utils.heal(p);
                        p.sendMessage(Utils.getServerPrefix() + Utils.colorize("Du wurdest von &b" + sender.getName() + "&f geheilt"));
                        return true;
                    }
                } else if (Bukkit.getPlayer(args[0]) != null) {
                    Player toHeal = Bukkit.getPlayer(args[0]);
                    Utils.heal(toHeal);
                    toHeal.sendMessage(Utils.getServerPrefix() + Utils.colorize("Du wurdest von &b" + sender.getName() + "&f geheilt"));
                    return true;
                }

            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        } else if (command.getLabel().equalsIgnoreCase("difficulty")) {
            if (sender.hasPermission("core.canChangeDifficulty") || sender.getName().equalsIgnoreCase("console")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("peaceful")) {
                        world.setDifficulty(Difficulty.PEACEFUL);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&a PEACEFUL&f!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("easy")) {
                        world.setDifficulty(Difficulty.EASY);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&a EINFACH&f!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("normal")) {
                        world.setDifficulty(Difficulty.NORMAL);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&e NORMAL&f!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("hard")) {
                        world.setDifficulty(Difficulty.HARD);
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Die Schwierigkeit ist jetzt&c SCHWER&f!"));
                        return true;
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/difficulty <peaceful, easy, normal, hard>&f um die Schwierigkeit zu ändern!"));
                        return false;
                    }
                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }

        }  else if (command.getLabel().equalsIgnoreCase("tps")) {
            if (sender.hasPermission("core.canShowTPS") || sender.getName().equalsIgnoreCase("console")) {
                double[] ticksPerSeconds = MinecraftServer.getServer().recentTps;
                sender.sendMessage(Utils.getServerPrefix() + ticksPerSeconds.toString());
                return true;
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        } else if (command.getLabel().equalsIgnoreCase("advancements")) {
            if (args.length == 1) {
                if (sender.isOp()) {
                    if (args[0].equalsIgnoreCase("show")) {
                        CoreMain.showAdvancements = true;
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&aDie Advancements werden jetzt angezeigt!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("hide")) {
                        CoreMain.showAdvancements = false;
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDie Advancements sind jetzt versteckt!"));
                        return true;
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/advancements <show, hide>&f um die Advancements zu zeigen, bzw. verstecken!"));
                        return false;
                    }
                } else {
                    sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                    return false;
                }
            } else {
                sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/advancements <show, hide>&f um die Advancements zu zeigen, bzw. verstecken!"));
                return false;
            }
        } else if (command.getLabel().equalsIgnoreCase("ping")) {
            sender.sendMessage(Utils.getPrefix("Server") + Utils.colorize("Dein Ping: &e" + Utils.getPing((Player) sender) + "&fms"));
            return true;
        } else if (command.getLabel().equalsIgnoreCase("sethp")) {
            if (sender.hasPermission("core.canheal")) {
                if (args.length == 1) {
                    if (Double.parseDouble(args[0]) >= 0 && Double.parseDouble(args[0]) <= 20) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.setHealth(Double.parseDouble(args[0]));
                            p.sendMessage(Utils.getServerPrefix() + Utils.colorize("Euer Leben wurde auf &c" + Double.parseDouble(args[0]) / 2 + "❤&f gesetzt!"));
                            return true;
                        }
                    } else {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/setHP <0-20>&f um die Advancements zu zeigen, bzw. verstecken!"));
                        return false;
                    }
                } else if (args.length == 2) {
                    Player toChange = Bukkit.getPlayer(args[1]);
                    toChange.setHealth(Double.parseDouble(args[0]));
                    toChange.sendMessage(Utils.getServerPrefix() + Utils.colorize("Dein Leben wurde auf &c" + Double.parseDouble(args[0]) / 2 + "❤&f gesetzt!"));
                    return true;
                } else {
                    sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("Verwende &6/setHP <0-20>&f um die Advancements zu zeigen, bzw. verstecken!"));
                    return false;
                }
            } else {
                sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        }
        return false;
    }
}
