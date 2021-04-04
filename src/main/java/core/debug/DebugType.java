package core.debug;


import java.util.HashMap;

public enum DebugType {
    PLAYER, SERVER, ERROR, PLUGIN, DATABASE, CHAT, BUNGEE, GUI;

    private static final HashMap<DebugType, String> typeColors = new HashMap<DebugType, String>() {{
        put(PLAYER, "#b5fc03");
        put(SERVER, "#fcba03");
        put(ERROR, "FF0000");
        put(PLUGIN, "#ff00b3");
        put(DATABASE, "#00ffbb");
        put(CHAT, "#ff5e00");
        put(BUNGEE, "#d0ff00");
        put(GUI, "#eb4034");
    }};

    public static String getDebugColor(DebugType type) {
        return typeColors.get(type);
    }
}