package core.permissions;

import core.core.CoreMain;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PermissionConverter {

    private static CoreMain coreMain;

    public PermissionConverter(CoreMain coreMain) {
        PermissionConverter.coreMain = coreMain;
    }

    public static String convertIntToBinary(Integer num) {
        return String.format("%16s", Integer.toBinaryString(num)).replace(' ', '0');
    }

    public static int convertBinaryToInt(String string) {
        return Integer.parseInt(string, 2);
    }

    public static HashMap<Permission, Boolean> generatePermissions(String string) {
        HashMap<Permission, Boolean> permissionBooleanHashMap = new HashMap<Permission, Boolean>();
        for (int i = 15; i > 15 - Permission.permissionList.size(); i--) {
            boolean value = string.charAt(i) == '1';
            permissionBooleanHashMap.put(Permission.permissionList.get(15 - i), value);
        }
        return permissionBooleanHashMap;
    }

    public static HashMap<Permission, Boolean> generatePermissions(Player player) {
        HashMap<Permission, Boolean> permissionBooleanHashMap = new HashMap<Permission, Boolean>();
        String string = convertIntToBinary(coreMain.mySQLPermissions.getPermissions(player.getUniqueId()));
        for (int i = 15; i > 15 - Permission.permissionList.size(); i--) {
            boolean value = string.charAt(i) == '1';
            permissionBooleanHashMap.put(Permission.permissionList.get(15 - i), value);
        }
        return permissionBooleanHashMap;
    }


}
