package core.settings;

import core.Utils;
import core.item.ItemCreator;
import core.settings.setting.Setting;
import core.settings.setting.SettingCycle;
import core.settings.setting.SettingsType;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SettingsInventoryBuilder {

    private static final List<Integer> usableSlots = new ArrayList<>() {{
        add(0);
        add(2);
        add(4);
        add(6);
        add(8);
        add(18);
        add(20);
        add(22);
        add(24);
        add(26);
        add(36);
        add(38);
        add(40);
        add(42);
        add(44);
    }};

    public static void fillInventoryBlanks(Inventory inventory, ItemStack itemStack){
        int size = inventory.getSize();

        for(int i = size - 1; i >= 0; i--){
            inventory.setItem(i, itemStack);
        }
    }

    public static void addPresetButton(Inventory inventory){
        ItemStack itemStack = ItemCreator.createItemStack(Material.COMMAND_BLOCK, 1, "Presets");
        int lastSlot = inventory.getSize() - 1;

        inventory.setItem(lastSlot, itemStack);
    }

    public static void addSettings(Inventory inventory, List<Setting<?>> settings){

        int i = 0;
        for(Setting<?> setting : settings){

            SettingsType type = setting.getType();
            ItemStack itemStack = ItemCreator.createItemStack(setting.getMaterial(), 1, setting.getName());
            ItemCreator.hideEnchants(itemStack);
            ItemCreator.addLore(itemStack, setting.getDescription());

            switch (type){
                case CLICK:
                    ItemCreator.addLore(itemStack, Utils.colorize("&8Aktueller Wert: &6" + setting.value));
                    break;
                case CYCLE:
                    SettingCycle settingCycle = (SettingCycle) setting;
                    ItemCreator.addLore(itemStack, Utils.colorize("&8Aktueller Wert: &6" + settingCycle.getValueAsString()));
                    break;
                case SWITCH:
                    ItemCreator.enchant(itemStack, (boolean) setting.getValue());
                    boolean value = (boolean) setting.getValue();
                    if(value){
                        ItemCreator.addLore(itemStack, Utils.colorize("&8Aktueller Wert: &a" + setting.getValue()));
                    }else{
                        ItemCreator.addLore(itemStack, Utils.colorize("&8Aktueller Wert: &c" + setting.getValue()));
                    }
                    break;
            }
            inventory.setItem(usableSlots.get(i), itemStack);
            i++;
        }
    }

}
