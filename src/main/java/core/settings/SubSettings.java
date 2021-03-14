package core.settings;

import core.Utils;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class SubSettings extends Settings {

    public SubSettings(String name, Plugin plugin, PluginSettings masterSettings) {
        super(name, plugin, masterSettings);
        this.addSetting(Utils.colorize("&cZurück"), new ArrayList<String>(), Material.BARRIER, false);
    }
}
