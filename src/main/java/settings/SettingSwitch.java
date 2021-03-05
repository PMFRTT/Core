package settings;

import org.bukkit.Material;

public class SettingSwitch extends Setting{

    private boolean enabled;

    public SettingSwitch(String name, String description, Material material, boolean enabled) {
       super(name, description, SettingsType.SWITCH, material);
       this.enabled = enabled;
    }

    public void changeSettingValue() {
        if (super.getType() == SettingsType.SWITCH) {
            this.enabled = !this.enabled;
        }
    }

    public boolean getSettingValue() {
        return this.enabled;
    }

    public String getName() {
        return super.getName();
    }

    public Material getMaterial() {
        return super.getMaterial();
    }

    public String getDescription() {
        return super.getDescription();
    }
}
