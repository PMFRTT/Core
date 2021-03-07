package settings;


import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PluginSettings {

    private String PluginName = " ";
    private final List<Setting> SettingsList = new ArrayList<Setting>();
    private final HashMap<String, Setting> SettingsMap = new HashMap<String, Setting>();
    private final SettingsInventory settingsInventory;

    public PluginSettings(String name, Plugin plugin) {
        this.PluginName = name;
        settingsInventory = new SettingsInventory(this, plugin);
    }

    public void addSetting(String name, String description, Material material, List<Integer> values) {
        SettingCycle setting = new SettingCycle(name, description, material, values);
        this.SettingsMap.put(name, setting);
        this.SettingsList.add(setting);
    }

    public void addSetting(String name, String description, Material material, List<Integer> values, List<String> mappedValues) {
        SettingCycle setting = new SettingCycle(name, description, material, values, mappedValues);
        this.SettingsMap.put(name, setting);
        this.SettingsList.add(setting);
    }

    public void addSetting(String name, String description, Material material, boolean enabled) {
        SettingSwitch setting = new SettingSwitch(name, description, material, enabled);
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
