package settings;


import core.core.CoreMain;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PluginSettings {

    private String PluginName = "";
    private final List<Setting> SettingsList = new ArrayList<Setting>();
    private final HashMap<String, Setting> SettingsMap = new HashMap<String, Setting>();
    private final SettingsInventory settingsInventory;

    public PluginSettings(String name, CoreMain corePlugin) {
        this.PluginName = name;
        settingsInventory = new SettingsInventory(this, corePlugin);
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
            this.SettingsMap.get(name).changeSettingValue();
        }
    }

    public boolean getSettingValue(String name) {
        return this.SettingsMap.get(name).getSettingValue();
    }

    public String getPluginName() {
        if (this.PluginName != null) {
            return this.PluginName;
        }else{
            return "error";
        }
    }

    public HashMap<String, Setting> getSettingsMap() {
        return this.SettingsMap;
    }

    public List<Setting> getSettingsList() {
        return this.SettingsList;
    }

    public SettingsInventory getSettingsInventory() {
        return this.settingsInventory;
    }

}
