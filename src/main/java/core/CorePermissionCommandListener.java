package core;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;
import java.util.Map;

public class CorePermissionCommandListener implements CommandExecutor {

    CoreAccessPermissionFile accessPermissionFile;
    CoreMain coreMain;

    public CorePermissionCommandListener(CoreMain coreMain, CoreAccessPermissionFile accessPermissionFile){
        this.coreMain = coreMain;
        this.accessPermissionFile = accessPermissionFile;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;


        if (command.getLabel().equalsIgnoreCase("allow")) {
            if (sender.getName() == "CONSOLE") {
                if (args[1].equalsIgnoreCase("gm")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (!attachment.getPermissions().get("core.canchangegamemode")) {
                        attachment.setPermission("core.canChangeGamemode", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf die Core-Commands &6/gamemode; /gm&2!"));
                    }
                }
                if (args[1].equalsIgnoreCase("weather")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (!attachment.getPermissions().get("core.canchangeweather")) {
                        attachment.setPermission("core.canChangeWeather", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf den Core-Command &6/weather&2!"));
                    }
                }
                if (args[1].equalsIgnoreCase("time")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (!attachment.getPermissions().get("core.canchangetime")) {
                        attachment.setPermission("core.canChangeTime", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf den Core-Command &6/time&2!"));
                    }
                }
                if (args[1].equalsIgnoreCase("heal")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (!attachment.getPermissions().get("core.canheal")) {
                        attachment.setPermission("core.canHeal", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf den Core-Command &6/heal&2!"));
                    }
                }
                if (args[1].equalsIgnoreCase("difficulty")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (!attachment.getPermissions().get("core.canchangedifficulty")) {
                        attachment.setPermission("core.canChangeDifficulty", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf die Core-Commands &6/difficulty; /d&2!"));
                    }
                }
                if (args[1].equalsIgnoreCase("invsee")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (!attachment.getPermissions().get("core.canseeinv")) {
                        attachment.setPermission("core.canSeeINV", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf den Core-Command &6/invsee"));
                    }
                }
                if(args[1].equalsIgnoreCase("teleport")){
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if(!attachment.getPermissions().get("core.canTP")){
                        attachment.setPermission("core.canTP", true);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt Rechte auf die Core-Commands &6/tp; /teleport&c!"));
                    }
                }
                if (args[1].equalsIgnoreCase("all")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment permissionAttachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    permissionAttachment.setPermission("core.canChangeGamemode", true);
                    permissionAttachment.setPermission("core.canChangeWeather", true);
                    permissionAttachment.setPermission("core.canChangeTime", true);
                    permissionAttachment.setPermission("core.canHeal", true);
                    permissionAttachment.setPermission("core.canChangeDifficulty", true);
                    permissionAttachment.setPermission("core.canShowTPS", true);
                    permissionAttachment.setPermission("core.canSeeINV", true);
                    permissionAttachment.setPermission("core.canTP", true);
                    CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), permissionAttachment);
                    accessPermissionFile.updatePermissions(player);
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&2Du hast jetzt Rechte auf alle Core-Commands!"));

                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
        } else if (command.getLabel().equalsIgnoreCase("disallow")) {
            if (sender.getName() == "CONSOLE") {
                if (args[1].equalsIgnoreCase("gm")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (attachment.getPermissions().get("core.canchangegamemode")) {
                        attachment.setPermission("core.canChangeGamemode", false);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf die Core-Commands &6/gamemode; /gm&c!"));
                    }
                }
                if (args[1].equalsIgnoreCase("weather")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (attachment.getPermissions().get("core.canchangeweather")) {
                        attachment.setPermission("core.canChangeWeather", false);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf den Core-Command &6/weather&c!"));
                    }
                }
                if (args[1].equalsIgnoreCase("time")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (attachment.getPermissions().get("core.canchangetime")) {
                        attachment.setPermission("core.canChangeTime", false);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf den Core-Command &6/time&c!"));
                    }
                }
                if (args[1].equalsIgnoreCase("heal")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (attachment.getPermissions().get("core.canheal")) {
                        attachment.setPermission("core.canHeal", false);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf den Core-Command &6/heal&c!"));
                    }
                }
                if (args[1].equalsIgnoreCase("difficulty")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if (attachment.getPermissions().get("core.canchangedifficulty")) {
                        attachment.setPermission("core.canChangeDifficulty", false);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf den Core-Command &6/difficulty&c!"));
                    }
                }
                if(args[1].equalsIgnoreCase("teleport")){
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment attachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    if(attachment.getPermissions().get("core.canTP")){
                        attachment.setPermission("core.canTP", false);
                        CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), attachment);
                        accessPermissionFile.updatePermissions(player);
                        player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf die Core-Commands &6/tp; /teleport&c!"));
                    }
                }
                if (args[1].equalsIgnoreCase("all")) {
                    player = Bukkit.getServer().getPlayer(args[0]);
                    PermissionAttachment permissionAttachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    permissionAttachment.setPermission("core.canChangeGamemode", false);
                    permissionAttachment.setPermission("core.canChangeWeather", false);
                    permissionAttachment.setPermission("core.canChangeTime", false);
                    permissionAttachment.setPermission("core.canHeal", false);
                    permissionAttachment.setPermission("core.canChangeDifficulty", false);
                    permissionAttachment.setPermission("core.canShowTPS", false);
                    permissionAttachment.setPermission("core.canSeeINV", false);
                    permissionAttachment.setPermission("core.canTP", false);
                    CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), permissionAttachment);
                    accessPermissionFile.updatePermissions(player);
                    player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu hast jetzt keine Rechte mehr auf die Core-Commands!"));

                }
            } else {
                player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen!"));
                return false;
            }
            return false;
        }else if (command.getLabel().equalsIgnoreCase("permissions")) {
            if (sender.getName().equalsIgnoreCase("console") || sender.isOp()) {
                if (args.length == 0) {
                    PermissionAttachment permissionAttachment = CoreMain.permissionAttachmentHashMap.get(player.getUniqueId());
                    Map<String, Boolean> permissions = permissionAttachment.getPermissions();
                    List<String> permissionValues = accessPermissionFile.getPermissions(permissions);
                    for (String permission : permissionValues) {
                        sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&b" + permission));
                    }
                    return true;
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        accessPermissionFile.readFileToHashMap();
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            accessPermissionFile.createPermissionSave(p);
                        }
                    } else {
                        PermissionAttachment permissionAttachment = CoreMain.permissionAttachmentHashMap.get(Bukkit.getPlayer(args[0]).getUniqueId());
                        Map<String, Boolean> permissions = permissionAttachment.getPermissions();
                        List<String> permissionValues = accessPermissionFile.getPermissions(permissions);
                        for (String permission : permissionValues) {
                            sender.sendMessage(Utils.getServerPrefix() + Utils.colorize("&b" + permission));
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }
}
