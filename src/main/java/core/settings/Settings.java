package core.settings;

import core.settings.Setting.Setting;
import core.settings.Setting.SettingCycle;
import core.settings.Setting.SettingSwitch;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Settings {

    private String PluginName = " ";
    private static final List<Setting> SettingsList = new ArrayList<Setting>();
    public List<Setting> SettingsListPerSetting = new ArrayList<Setting>();
    private final HashMap<String, Setting> SettingsMap = new HashMap<String, Setting>();
    private final SettingsInventory settingsInventory;
    private PluginSettings masterSettings = null;
    private final boolean isMasterSettings;
    private static final List<Settings> subSettings = new ArrayList<Settings>();

    public Settings(String name, Plugin plugin, PluginSettings masterSettings) {
        this.PluginName = name;
        this.masterSettings = masterSettings;
        this.isMasterSettings = false;
        settingsInventory = new SettingsInventory(this, plugin);
        subSettings.add(this);
    }

    public Settings(String name, Plugin plugin) {
        this.PluginName = name;
        this.isMasterSettings = true;
        settingsInventory = new SettingsInventory(this, plugin);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, List<Integer> values) {
        SettingCycle setting = new SettingCycle(name, description, material, values);
        this.SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues) {
        SettingCycle setting = new SettingCycle(name, description, material, values, mappedValues);
        this.SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues, List<Material> mappedMaterials) {
        SettingCycle setting = new SettingCycle(name, description, material, values, mappedValues, mappedMaterials);
        this.SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, boolean enabled) {
        SettingSwitch setting = new SettingSwitch(name, description, material, enabled);
        this.SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, boolean enabled, SubSettings subSettings) {
        SettingSwitch setting = new SettingSwitch(name, description, material, enabled, subSettings);
        this.SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
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
        } else {
            return "error";
        }
    }

    public HashMap<String, Setting> getSettingsMap() {
        return this.SettingsMap;
    }

    public List<Setting> getSettingsList() {
        return this.SettingsListPerSetting;
    }

    public SettingsInventory getSettingsInventory() {
        return this.settingsInventory;
    }

    public Settings getMasterSettings() {
        if (!this.isMasterSettings) {
            return this.masterSettings;
        } else return this;
    }

    public List<Settings> getSubSettings(){
        return subSettings;
    }

    public List<Setting> getAllSettings(){
        return Settings.SettingsList;
    }
}
