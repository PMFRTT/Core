package settings;

import org.bukkit.Material;

import java.util.List;

public class SettingCycle extends Setting {

    List<Integer> values;
    int index = 0;
    int value;

    public SettingCycle(String name, List<String> description, Material material, List<Integer> values) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
    }

    public void changeSettingValue() {
        if (super.getType() == SettingsType.CYCLE) {
            if (this.index < values.size() - 1) {
                index++;
            } else {
                index = 0;
            }
            value = values.get(index);
        }
    }

    public String getName() {
        return super.getName();
    }

    public Material getMaterial() {
        return super.getMaterial();
    }

    public List<String> getDescription() {
        return super.getDescription();
    }

    public int getValue() {
        return this.value;
    }

    public List<Integer> getValues() {
        return this.values;
    }

    public int getIndex() {
        return this.index;
    }

}
