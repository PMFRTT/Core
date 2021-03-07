package settings;

import org.bukkit.Material;

import java.util.List;

public class SettingCycle extends Setting {

    List<Integer> values;
    int index = 0;
    int value;
    List<String> mappedValues;

    public SettingCycle(String name, String description, Material material, List<Integer> values) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
    }

    public SettingCycle(String name, String description, Material material, List<Integer> values, List<String> mappedValues) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
        this.mappedValues = mappedValues;
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

    public String getDescription() {
        return super.getDescription();
    }

    public int getValue() {
        return this.value;
    }

    public String getValueAsString(){
        if(this.mappedValues.isEmpty()){
            return String.valueOf(this.getValue());
        }else{
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
