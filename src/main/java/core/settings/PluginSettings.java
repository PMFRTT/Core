package core.settings;

import org.bukkit.plugin.Plugin;


public abstract class PluginSettings extends Settings{

    public PluginSettings(String name, Plugin plugin) {
        super(name, plugin);
    }
}
