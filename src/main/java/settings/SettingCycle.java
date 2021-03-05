package settings;

import org.bukkit.Material;

public class SettingCycle extends Setting {

    int[] values;
    int index = 0;
    int value;

    public SettingCycle(String name, String description, Material material, int[] values) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
    }

    public void changeSettingValue() {
        if (super.getType() == SettingsType.CYCLE) {
            if(this.index > values.length){
                index++;
                value = values[index];
            }
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

    public int getValue(){
        return this.value;
    }

    public int[] getValues(){
        return this.values;
    }

    public int getIndex(){
        return this.index;
    }

}
