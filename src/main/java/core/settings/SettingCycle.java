package core.settings;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SettingCycle extends Setting {

    List<Integer> values;
    int index = 0;
    int value;
    List<String> mappedValues;
    List<Material> mappedMaterials;

    public SettingCycle(String name, ArrayList<String> description, Material material, List<Integer> values) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
    }

    public SettingCycle(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
        this.value = values.get(1);
        this.mappedValues = mappedValues;
    }

    public SettingCycle(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues, List<Material> mappedMaterials) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
        this.value = values.get(1);
        this.mappedValues = mappedValues;
        this.mappedMaterials = mappedMaterials;
    }

    public void cycleUp() {
        if (super.getType() == SettingsType.CYCLE) {
            if (this.index < values.size() - 1) {
                index++;
            } else {
                index = 0;
            }
            value = values.get(index);
        }
    }

    public void cycleDown() {
        if (super.getType() == SettingsType.CYCLE) {
            if (this.index > 0) {
                index--;
            } else {
                index = values.size() - 1;
            }
            value = values.get(index);
        }
    }

    public String getName() {
        return super.getName();
    }

    public Material getMaterial() {
        if (this.mappedMaterials != null) {
            return this.mappedMaterials.get(this.index);
        }else return super.getMaterial();
    }

    public ArrayList<String> getDescription() {
        return super.getDescription();
    }

    public int getValue() {
        value = getValues().get(getIndex());
        return this.value;
    }

    public String getValueAsString() {
        if (this.mappedValues == null) {
            return String.valueOf(getValue());
        } else {
            return this.mappedValues.get(this.getIndex());
        }
    }

    public List<Integer> getValues() {
        return this.values;
    }

    public int getIndex() {
        return this.index;
    }

}
