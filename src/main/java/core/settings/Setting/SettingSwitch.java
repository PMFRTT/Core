package core.settings.Setting;

import core.settings.SubSettings;
import org.bukkit.Material;

import java.util.ArrayList;

public class SettingSwitch extends Setting<Boolean> {

    private SubSettings subSettings;

    public SettingSwitch(String name, ArrayList<String> description, Material material, boolean enabled) {
       super(name, description, SettingsType.SWITCH, material);
       this.enabled = enabled;
    }

    public SettingSwitch(String name, ArrayList<String> description, Material material, boolean enabled, SubSettings subSettings) {
        super(name, description, SettingsType.SWITCH, material);
        this.enabled = enabled;
        this.subSettings = subSettings;
    }

    public void changeSettingValue() {
        this.enabled = !this.enabled;
    }

    public SubSettings getSubSettings(){
        return this.subSettings;
    }

    public String getName() {
        return super.getName();
    }

    public Material getMaterial() {
        return super.getMaterial();
    }

    @Override
    public Boolean getValue() {
        return this.enabled;
    }

    public ArrayList<String> getDescription() {
        return super.getDescription();
    }

    @Override
    public SettingsType getType() {
        return SettingsType.SWITCH;
    }
}
