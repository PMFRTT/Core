package core.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Permission {
    GAMEMODE, WEATHER, TIME, HEAL, DIFFICULTY, INVENTORY, TELEPORT, FLY, ALL;

    public static final List<Permission> permissionList = new ArrayList<>() {{
        add(GAMEMODE);
        add(WEATHER);
        add(TIME);
        add(HEAL);
        add(DIFFICULTY);
        add(INVENTORY);
        add(TELEPORT);
        add(FLY);
    }};

    public static final HashMap<Permission, Integer> permissionIndexMap = new HashMap<>() {{
        put(GAMEMODE, 15);
        put(WEATHER, 14);
        put(TIME, 13);
        put(HEAL, 12);
        put(DIFFICULTY, 11);
        put(INVENTORY, 10);
        put(TELEPORT, 9);
        put(FLY, 8);
    }};
}
