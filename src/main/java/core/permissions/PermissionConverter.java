package core.permissions;

import org.bukkit.entity.Player;

import java.util.HashMap;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class PermissionConverter {

    public static String convertIntToBinary(Integer num) {
        return String.format("%16s", Integer.toBinaryString(num)).replace(' ', '0');
    }

    public static int convertBinaryToInt(String string) {
        return Integer.parseInt(string, 2);
    }

    public static void generatePermissions(String string) {
        HashMap<Permission, Boolean> permissionBooleanHashMap = new HashMap<>();
        for (int i = 15; i > 15 - Permission.permissionList.size(); i--) {
            boolean value = string.charAt(i) == '1';
            permissionBooleanHashMap.put(Permission.permissionList.get(15 - i), value);
        }
    }

    public static HashMap<Permission, Boolean> generatePermissions(Player player) {
        HashMap<Permission, Boolean> permissionBooleanHashMap = new HashMap<>();
        String string = convertIntToBinary(PermissionHandler.getDataset().getPermissions(player.getUniqueId()));
        for (int i = 15; i > 15 - Permission.permissionList.size(); i--) {
            boolean value = string.charAt(i) == '1';
            permissionBooleanHashMap.put(Permission.permissionList.get(15 - i), value);
        }
        return permissionBooleanHashMap;
    }


}
