package core.core;

import org.bukkit.plugin.java.JavaPlugin;

public final class CoreMain extends JavaPlugin {

    public void onEnable() {
        CoreHandler.init(this);
    }

    public void onDisable() {
        CoreHandler.endSQLConnection();
    }
}