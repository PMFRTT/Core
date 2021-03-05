package settings;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsInventory {

    PluginSettings pluginSettings;
    private final Inventory inventory;
    private List<Integer> usableSlots = new ArrayList<Integer>() {{
        add(0);
        add(4);
        add(16);
    }};

    public SettingsInventory(PluginSettings pluginSettings) {
        this.pluginSettings = pluginSettings;
        inventory = Bukkit.createInventory(null, 54, pluginSettings.getPluginName());
    }

    public SettingsInventory(PluginSettings pluginSettings, List<Integer> usableSlots) {
        this.pluginSettings = pluginSettings;
        this.usableSlots = usableSlots;
        inventory = Bukkit.createInventory(null, 54, pluginSettings.getPluginName());
    }

    private void buildInventory() {
        int i = 0;
        if (!this.pluginSettings.getSettingsList().isEmpty()) {
            for (Setting setting : this.pluginSettings.getSettingsList()) {
                ItemStack itemStack = new ItemStack(setting.getMaterial());
                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(setting.getName());
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.setLore(new ArrayList<String>() {{
                    add(setting.getDescription());
                }});
                if (setting.getSettingValue()) {
                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                } else {
                    itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                }
                itemStack.setItemMeta(itemMeta);
                this.inventory.setItem(this.usableSlots.get(i), itemStack);
                i++;
            }
        }
    }

    private List<Integer> defaultSlots() {
        return new ArrayList<Integer>() {{
            add(0);
        }};
    }

    public Inventory getInventory() {
        buildInventory();
        return this.inventory;
    }

}
