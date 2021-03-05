package settings;

import org.bukkit.Material;

public abstract class Setting {

    private final String name;
    private final String description;
    private final SettingsType type;
    private final Material material;

    public Setting(String name, String description, SettingsType type, Material material){
        this.name = name;
        this.description = description;
        this.type = type;
        this.material = material;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public SettingsType getType(){
        return this.type;
    }

    public Material getMaterial(){
        return this.material;
    }

}
