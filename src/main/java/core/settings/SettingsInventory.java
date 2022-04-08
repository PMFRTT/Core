package core.settings;

import core.item.ItemCreator;
import core.settings.setting.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class SettingsInventory{

    private final Settings settings;


    SettingsInventoryListener settingsInventoryListener;

    private final Inventory inventory;
    private final HashMap<Integer, Setting> slotSettingsMap = new HashMap<>();


    public SettingsInventory(Settings settings, Plugin plugin) {
        this.settings = settings;
        inventory = Bukkit.createInventory(null, 45, settings.getPluginName());
        settingsInventoryListener = new SettingsInventoryListener(settings, plugin, inventory);
    }

    private void buildInventory() {

        SettingsInventoryBuilder.fillInventoryBlanks(this.inventory, ItemCreator.createItemStack(Material.BLACK_STAINED_GLASS_PANE, 1, "--"));
        SettingsInventoryBuilder.addPresetButton(this.inventory);
        SettingsInventoryBuilder.addSettings(this. inventory, settings.getSettingsList());

        /*int i = 0;
        if (!this.settings.getSettingsList().isEmpty()) {
            for (Setting setting : this.settings.getSettingsList()) {
                ItemStack itemStack = new ItemStack(setting.getMaterial());
                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(setting.getName());
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                List<String> temp = new ArrayList<String>(setting.getDescription());
                if (setting instanceof SettingSwitch) {
                    if ((Boolean) setting.getValue()) {
                        temp.add(Utils.colorize("&8Aktueller Wert: &a" + setting.getValue()));
                        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    } else {
                        temp.add(Utils.colorize("&8Aktueller Wert: &c" + setting.getValue()));
                        itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                } else if (setting instanceof SettingCycle) {
                    SettingCycle settingCycle = (SettingCycle) setting;
                    itemMeta.setDisplayName(setting.getName());
                    temp.add(Utils.colorize("&8Aktueller Wert: &6" + settingCycle.getValueAsString()));
                } else if (setting instanceof SettingClick) {
                    temp.add(Utils.colorize("&8Aktueller Wert: &6" + setting.value));
                }
                itemMeta.setLore(temp);
                itemStack.setItemMeta(itemMeta);
                this.inventory.setItem(this.usableSlots.get(i), itemStack);
                this.slotSettingsMap.put(this.usableSlots.get(i), setting);
                i++;
            }
        }*/
    }

    public Inventory getInventory() {
        buildInventory();
        return this.inventory;
    }
}
