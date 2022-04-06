package core.settings.setting;

import core.settings.SubSettings;
import org.bukkit.Material;

import java.util.ArrayList;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SettingClick extends Setting {

    private SubSettings subSettings;

    public SettingClick(String name, ArrayList<String> description, SettingsType type, Material material) {
        super(name, description, type, material);
    }
    public SettingClick(String name, ArrayList<String> description, SettingsType type, Material material, SubSettings subSettings) {
        super(name, description, type, material);
        this.subSettings = subSettings;
    }

    @Override
    public Object getValue() {
        return null;
    }

    public SubSettings getSubSettings(){
        return this.subSettings;
    }
}
