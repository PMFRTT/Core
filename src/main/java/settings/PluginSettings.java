package settings;

import java.util.HashMap;

public abstract class PluginSettings {

    private final String PluginName;
    private final HashMap<String, Setting> SettingsMap = new HashMap<String, Setting>();

    public PluginSettings(String name){
        this.PluginName = name;
    }

    public void addSetting(String name, String description, boolean enabled){
        this.SettingsMap.put(name, new Setting(name, description, enabled));
    }

    public Setting getSettingbyName(String name){
        if(SettingsMap.containsKey(name)){
            return this.SettingsMap.get(name);
        }else{
            return null;
        }
    }

    public void changeSettingValue(String name, boolean enabled){
        if(this.SettingsMap.containsKey(name)){
            this.SettingsMap.get(name).changeSettingValue(enabled);
        }
    }

    public boolean getSettingValue(String name){
        return this.SettingsMap.get(name).getSettingValue();
    }

}
