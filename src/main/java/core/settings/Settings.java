package core.settings;

import core.settings.Setting.*;
import core.settings.presets.PresetHandler;
import core.settings.presets.PresetInventory;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Settings {

    private String PluginName = " ";

    private final Plugin plugin;

    private static final List<Setting> SettingsList = new ArrayList<Setting>();
    public List<Setting> SettingsListPerSetting = new ArrayList<Setting>();
    private static final HashMap<String, Setting> SettingsMap = new HashMap<String, Setting>();
    private final SettingsInventory settingsInventory;
    private PluginSettings masterSettings = null;
    private final boolean isMasterSettings;
    private static final List<Settings> subSettings = new ArrayList<Settings>();

    private final PresetHandler presetHandler;
    private final PresetInventory presetInventory;


    public Settings(String name, Plugin plugin, PluginSettings masterSettings) {
        this.plugin = plugin;
        this.PluginName = name;
        this.masterSettings = masterSettings;
        this.isMasterSettings = false;
        settingsInventory = new SettingsInventory(this, plugin);
        subSettings.add(this);
        presetHandler = new PresetHandler(this, plugin);
        this.presetInventory = new PresetInventory(plugin, this);
        presetHandler.createDirectory();
    }

    public Settings(String name, Plugin plugin) {
        this.plugin = plugin;
        this.PluginName = name;
        this.isMasterSettings = true;
        settingsInventory = new SettingsInventory(this, plugin);
        presetHandler = new PresetHandler(this, plugin);
        this.presetInventory = new PresetInventory(plugin, this);
        presetHandler.createDirectory();
    }

    public void addSetting(String name, ArrayList<String> description, Material material, List<Integer> values) {
        SettingCycle setting = new SettingCycle(name, description, material, values);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues) {
        SettingCycle setting = new SettingCycle(name, description, material, values, mappedValues);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues, List<Material> mappedMaterials) {
        SettingCycle setting = new SettingCycle(name, description, material, values, mappedValues, mappedMaterials);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, boolean enabled) {
        SettingSwitch setting = new SettingSwitch(name, description, material, enabled);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, boolean enabled, SubSettings subSettings) {
        SettingSwitch setting = new SettingSwitch(name, description, material, enabled, subSettings);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material) {
        SettingClick setting = new SettingClick(name, description, SettingsType.CLICK, material);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }

    public void addSetting(String name, ArrayList<String> description, Material material, SubSettings subSettings) {
        SettingClick setting = new SettingClick(name, description, SettingsType.CLICK, material, subSettings);
        SettingsMap.put(name, setting);
        SettingsList.add(setting);
        SettingsListPerSetting.add(setting);
    }


    public Setting getSettingbyName(String name) {
        return SettingsMap.getOrDefault(name, null);
    }

    public String getPluginName() {
        if (this.PluginName != null) {
            return this.PluginName;
        } else {
            return "error";
        }
    }

    public HashMap<String, Setting> getSettingsMap() {
        return SettingsMap;
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

    public List<Settings> getSubSettings() {
        return subSettings;
    }

    public List<Setting> getAllSettings() {
        return Settings.SettingsList;
    }

    public PresetHandler getPresetHandler() {
        return this.presetHandler;
    }

    public PresetInventory getPresetInventory() {
        return presetInventory;
    }
}
