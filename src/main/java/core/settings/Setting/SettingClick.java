package core.settings.Setting;

import org.bukkit.Material;

import java.util.ArrayList;

public class SettingClick extends Setting {
    public SettingClick(String name, ArrayList<String> description, SettingsType type, Material material) {
        super(name, description, type, material);
    }
}
