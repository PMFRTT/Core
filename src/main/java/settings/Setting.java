package settings;

import org.bukkit.Material;

import java.util.List;

public abstract class Setting {

    private final String name;
    private final List<String> description;
    private final SettingsType type;
    private final Material material;

    public Setting(String name, List<String> description, SettingsType type, Material material){
        this.name = name;
        this.description = description;
        this.type = type;
        this.material = material;
    }

    public String getName(){
        return this.name;
    }

    public List<String> getDescription(){
        return this.description;
    }

    public SettingsType getType(){
        return this.type;
    }

    public Material getMaterial(){
        return this.material;
    }

}
