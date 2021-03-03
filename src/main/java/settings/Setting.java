package settings;

import org.bukkit.Material;

public class Setting {

    private final String name;
    private final String description;
    private boolean enabled;
    private final Material material;


    public Setting(String name, String description, boolean enabled, Material material) {
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.material = material;
    }

    public void changeSettingValue(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getSettingValue() {
        return this.enabled;
    }

    public String getName(){
        return this.name;
    }

    public Material getMaterial(){
        return this.material;
    }

    public String getDescription(){
        return this.description;
    }
}
