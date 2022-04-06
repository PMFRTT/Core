package core.settings;

import core.Utils;
import core.settings.setting.Setting;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SubSettings extends Settings {

    public SubSettings(String name, Plugin plugin, PluginSettings masterSettings) {
        super(name, plugin, masterSettings);
        this.addSetting(Utils.colorize("&cZurück"), new ArrayList<>(), Material.BARRIER);
    }

    @SuppressWarnings("rawtypes")
    public List<Setting> getSettings(){
        return this.getAllSettings();
    }
}
