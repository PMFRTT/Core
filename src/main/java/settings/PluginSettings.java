package settings;


import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PluginSettings {

    private final String PluginName;
    private final List<Setting> SettingsList = new ArrayList<Setting>();
    private final HashMap<String, Setting> SettingsMap = new HashMap<String, Setting>();

    public PluginSettings(String name) {
        this.PluginName = name;
    }

    public void addSetting(String name, String description, boolean enabled, Material material) {
        Setting setting = new Setting(name, description, enabled, material);

        this.SettingsMap.put(name, setting);
        this.SettingsList.add(setting);
    }

    public Setting getSettingbyName(String name) {
        if (SettingsMap.containsKey(name)) {
            return this.SettingsMap.get(name);
        } else {
            return null;
        }
    }

    public void changeSettingValue(String name, boolean enabled) {
        if (this.SettingsMap.containsKey(name)) {
            this.SettingsMap.get(name).changeSettingValue(enabled);
        }
    }

    public boolean getSettingValue(String name) {
        return this.SettingsMap.get(name).getSettingValue();
    }

    public String getPluginName() {
        return this.PluginName;
    }

    public HashMap<String, Setting> getSettingsMap() {
        return this.SettingsMap;
    }

    public List<Setting> getSettingsList(){
        return this.SettingsList;
    }

}
