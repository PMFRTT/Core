package settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsInventory {

    PluginSettings pluginSettings;
    private final Inventory inventory;
    private List<Integer> usableSlots = new ArrayList<Integer>(){{
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
                Material type;
                ItemStack itemStack = new ItemStack(setting.getMaterial());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(setting.getName());
                itemMeta.setLore(new ArrayList<String>() {{
                    add(setting.getDescription());
                }});
                itemStack.setItemMeta(itemMeta);
                this.inventory.setItem(this.usableSlots.get(i), itemStack);
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
