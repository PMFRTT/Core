package core.permissions;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PermissionCard {

    private final Player player;
    private final String permissionCode;
    private final int LENGTH = 10;

    private final List<String> permissions = new ArrayList<String>() {{
        add("change_gamemode");
        add("change_weather");
        add("change_time");
        add("heal");
        add("change_difficulty");
        add("see_inv");
        add("move");
        add("break_blocks");
        add("teleport");
    }};

    public PermissionCard(Player player, String permissionCode) {
        this.player = player;
        this.permissionCode = permissionCode;
    }

    public String[] getPermissions() {
        return null;
    }

    public String[] getPermission(int permissionCode) {
        return null;
    }

    public void updatePermissions(String permission, boolean state) { //where permission can be name or binary code
        boolean binary = isBinary(permission);

        if (binary) {

        } else {


            switch (permission) {

            }
        }
    }

    public void updatePermissions(int permissionCode, boolean state) {

    }

    private boolean isBinary(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Boolean> convertToBooleanArray() {
        List<Boolean> booleanArray = new ArrayList<Boolean>();
        int binaryCode = Integer.parseInt(permissionCode);
        for (int i = 0; i < LENGTH; i++) {
            if (i > 0) {
                if ((int) (binaryCode / Math.pow(10, i)) % 10 == 1) {
                    booleanArray.add(true);
                } else {
                    booleanArray.add(false);
                }
            } else {
                if ((binaryCode) % 10 == 1) {
                    booleanArray.add(true);
                } else {
                    booleanArray.add(false);
                }
            }
        }
        return booleanArray;
    }

}
