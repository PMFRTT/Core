package core.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Permission {
    GAMEMODE, WEATHER, TIME, HEAL, DIFFICULTY, INVENTORY, TELEPORT, ALL;

    public static List<Permission> permissionList = new ArrayList<Permission>() {{
        add(GAMEMODE);
        add(WEATHER);
        add(TIME);
        add(HEAL);
        add(DIFFICULTY);
        add(INVENTORY);
        add(TELEPORT);
    }};

    public static HashMap<Permission, Integer> permissionIndexMap = new HashMap<Permission, Integer>() {{
        put(GAMEMODE, 15);
        put(WEATHER, 14);
        put(TIME, 13);
        put(HEAL, 12);
        put(DIFFICULTY, 11);
        put(INVENTORY, 10);
        put(TELEPORT, 9);
    }};
}
