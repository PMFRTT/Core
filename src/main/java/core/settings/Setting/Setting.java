package core.settings.setting;

import org.bukkit.Material;

import java.util.ArrayList;

public abstract class Setting<T> {

    private final String name;
    private final ArrayList<String> description;
    private final SettingsType type;
    private final Material material;
    public int value;
    public boolean enabled;


    public Setting(String name, ArrayList<String> description, SettingsType type, Material material) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.material = material;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getDescription() {
        return this.description;
    }

    public SettingsType getType() {
        return this.type;
    }

    public Material getMaterial() {
        return this.material;
    }

    public abstract T getValue();

}
