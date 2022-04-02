package core.settings.Setting;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SettingCycle extends Setting<Integer> {

    List<Integer> values;
    int index = 0;
    List<String> mappedValues;
    List<Material> mappedMaterials;

    private Boolean blockUP = false;
    private Boolean blockDO = false;

    public SettingCycle(String name, ArrayList<String> description, Material material, List<Integer> values) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
        super.value = values.get(0);
    }

    public SettingCycle(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
        super.value = values.get(1);
        this.mappedValues = mappedValues;
        super.value = values.get(0);
    }

    public SettingCycle(String name, ArrayList<String> description, Material material, List<Integer> values, List<String> mappedValues, List<Material> mappedMaterials) {
        super(name, description, SettingsType.CYCLE, material);
        this.values = values;
        this.value = values.get(1);
        this.mappedValues = mappedValues;
        this.mappedMaterials = mappedMaterials;
        super.value = values.get(0);
    }

    public void cycleUp() {
        if (!this.blockUP) {
            if (super.getType() == SettingsType.CYCLE) {
                if (this.index < values.size() - 1) {
                    index++;
                } else {
                    index = 0;
                }
                value = values.get(index);
            }
        }
    }

    public void cycleDown() {
        if (!this.blockDO) {
            if (super.getType() == SettingsType.CYCLE) {
                if (this.index > 0) {
                    index--;
                } else {
                    index = values.size() - 1;
                }
                value = values.get(index);
            }
        }
    }

    public String getName() {
        return super.getName();
    }

    public Material getMaterial() {
        if (this.mappedMaterials != null) {
            return this.mappedMaterials.get(this.index);
        } else return super.getMaterial();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public ArrayList<String> getDescription() {
        return super.getDescription();
    }

    public String getValueAsString() {
        if (this.mappedValues == null) {
            return String.valueOf(value);
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

    public void blockCycleUP(Boolean block) {
        if (this.blockUP != block) {
            this.blockUP = block;
        }
    }

    public void blockCycleDO(Boolean block) {
        if (this.blockDO != block) {
            this.blockDO = block;
        }
    }

}
