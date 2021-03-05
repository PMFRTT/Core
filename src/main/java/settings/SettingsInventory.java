package settings;

import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettingsInventory implements Listener {

    private final CoreMain corePlugin;
    PluginSettings pluginSettings;
    private final Inventory inventory;
    private final HashMap<Integer, Setting> slotSettingsMap = new HashMap<Integer, Setting>();
    private List<Integer> usableSlots = new ArrayList<Integer>() {{
        add(0);
        add(4);
        add(16);
    }};

    public SettingsInventory(PluginSettings pluginSettings, CoreMain corePlugin) {
        this.pluginSettings = pluginSettings;
        this.corePlugin = corePlugin;
        inventory = Bukkit.createInventory(null, 54, pluginSettings.getPluginName());
        initialize();
    }

    public SettingsInventory(PluginSettings pluginSettings, List<Integer> usableSlots, CoreMain corePlugin) {
        this.pluginSettings = pluginSettings;
        this.usableSlots = usableSlots;
        this.corePlugin = corePlugin;
        inventory = Bukkit.createInventory(null, 54, pluginSettings.getPluginName());
        initialize();
    }

    public void initialize(){
        Bukkit.getPluginManager().registerEvents(this, corePlugin);
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
                this.slotSettingsMap.put(this.usableSlots.get(i), setting);
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

    public Setting getSettingfromSlot(int slot){
        return this.slotSettingsMap.getOrDefault(slot, null);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        System.out.println("test");
        if(Objects.equals(e.getClickedInventory(), this.inventory)){
           e.setCancelled(true);
           if(this.usableSlots.contains(e.getSlot())){
               getSettingfromSlot(e.getSlot()).changeSettingValue();
               buildInventory();
           }
        }
    }

}
