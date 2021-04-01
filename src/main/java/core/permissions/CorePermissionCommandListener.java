package core.permissions;

import core.Utils;
import core.core.CoreMain;
import core.core.CoreSendStringPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CorePermissionCommandListener implements CommandExecutor {

    CoreMain coreMain;
    StringBuilder permissionChanger;

    public CorePermissionCommandListener(CoreMain coreMain) {
        this.coreMain = coreMain;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (sender.getName().equals("CONSOLE") && args.length == 3) {
            if (command.getLabel().equalsIgnoreCase("allow")) {
                updatePermission(Bukkit.getPlayer(args[0]), args[1], '1', Boolean.getBoolean(args[2]));
            } else if (command.getLabel().equalsIgnoreCase("revoke")) {
                updatePermission(Bukkit.getPlayer(args[0]), args[1], '0', Boolean.getBoolean(args[2]));
            }
        } else {
            assert player != null;
            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDu verfügst nicht über die Rechte, diesen Command auszuführen oder du hast einen Command eingegeben der nicht existiert!"));
            return false;
        }
        return false;
    }

    private void updatePermission(Player player, String string, char value, boolean discrete) {

        Permission permission = null;

        switch (string.toUpperCase()) {
            case "GM", "GAMEMODE" -> permission = Permission.GAMEMODE;
            case "WEATHER" -> permission = Permission.WEATHER;
            case "TIME" -> permission = Permission.TIME;
            case "HEAL" -> permission = Permission.HEAL;
            case "DIFFICULTY" -> permission = Permission.DIFFICULTY;
            case "INVENTORY" -> permission = Permission.INVENTORY;
            case "TELEPORT" -> permission = Permission.TELEPORT;
            case "FLY" -> permission = Permission.FLY;
            case "ALL" -> permission = Permission.ALL;
        }

        if (permission != null) {
            if (permission != Permission.ALL) {
                if (!discrete) {
                    if (value == '1') {
                        CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&2Rechte Erhalten"), Utils.colorize("Du hast das Recht &a" + permission.toString() + " &ferhalten"));
                    } else {
                        CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&4Rechte Entzogen"), Utils.colorize("Du hast das Recht &c" + permission.toString() + " &fverloren"));
                    }
                }
                int index = Permission.permissionIndexMap.get(permission);
                permissionChanger = new StringBuilder((PermissionConverter.convertIntToBinary(CoreMain.mySQLPermissions.getPermissions(player.getUniqueId()))));
                permissionChanger.setCharAt(index, value);
                CoreMain.mySQLPermissions.setPermissions(player.getUniqueId(), PermissionConverter.convertBinaryToInt(permissionChanger.toString()));
            } else {
                for (Permission permission1 : Permission.permissionIndexMap.keySet()) {
                    updatePermission(player, permission1.name(), value, true);
                }
            }
        }
    }

}
