package core.settings;

import core.Utils;
import core.settings.Setting.Setting;
import core.settings.Setting.SettingCycle;
import core.settings.Setting.SettingSwitch;
import core.settings.Setting.SettingsType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettingsInventory implements Listener {

    Settings settings;
    private final Plugin plugin;
    private final Inventory inventory;
    private final HashMap<Integer, Setting> slotSettingsMap = new HashMap<Integer, Setting>();
    private List<Integer> usableSlots = new ArrayList<Integer>() {{
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

    public SettingsInventory(Settings settings, Plugin plugin) {
        this.settings = settings;
        this.plugin = plugin;
        inventory = Bukkit.createInventory(null, 45, settings.getPluginName());
        initialize();
    }

    public SettingsInventory(Settings settings, List<Integer> usableSlots, Plugin plugin) {
        this.settings = settings;
        this.usableSlots = usableSlots;
        this.plugin = plugin;
        inventory = Bukkit.createInventory(null, 54, settings.getPluginName());
        initialize();
    }

    public void initialize() {
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    private void buildInventory() {
        int i = 0;
        if (!this.settings.getSettingsList().isEmpty()) {
            for (Setting setting : this.settings.getSettingsList()) {
                ItemStack itemStack = new ItemStack(setting.getMaterial());
                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(setting.getName());
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                List<String> temp = new ArrayList<String>(setting.getDescription());
                if (setting.getType().equals(SettingsType.SWITCH)) {
                    SettingSwitch settingSwitch = (SettingSwitch) setting;
                    temp.add(Utils.colorize("&7Aktueller Wert: &6" + settingSwitch.getSettingValue()));
                    if (settingSwitch.getSettingValue()) {
                        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    } else {
                        itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                } else {
                    SettingCycle settingCycle = (SettingCycle) setting;
                    itemMeta.setDisplayName(setting.getName());
                    temp.add(Utils.colorize("&8Aktueller Wert: &6" + settingCycle.getValueAsString()));
                }
                itemMeta.setLore(temp);
                itemStack.setItemMeta(itemMeta);
                this.inventory.setItem(this.usableSlots.get(i), itemStack);
                this.slotSettingsMap.put(this.usableSlots.get(i), setting);
                i++;
            }
        }
    }

    public Inventory getInventory() {
        buildInventory();
        return this.inventory;
    }

    public Setting getSettingfromSlot(int slot) {
        return this.slotSettingsMap.getOrDefault(slot, null);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (Objects.equals(e.getClickedInventory(), this.inventory)) {
            e.setCancelled(true);
            if (this.usableSlots.contains(e.getSlot())) {
                if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.BARRIER)){
                    e.getWhoClicked().openInventory(settings.getMasterSettings().getSettingsInventory().getInventory());
                }
                else if (getSettingfromSlot(e.getSlot()).getType().equals(SettingsType.SWITCH)) {
                    SettingSwitch settingSwitch = (SettingSwitch) getSettingfromSlot(e.getSlot());
                    if (e.getClick().isShiftClick()) {
                        if (settingSwitch.getSubSettings() != null) {
                            e.getWhoClicked().openInventory(settingSwitch.getSubSettings().getSettingsInventory().getInventory());
                        }
                    } else if (e.getClick().isLeftClick()) {
                        settingSwitch.changeSettingValue();
                    }
                } else {
                    SettingCycle settingCycle = (SettingCycle) getSettingfromSlot(e.getSlot());
                    if (e.getClick().isLeftClick()) {
                        settingCycle.cycleUp();
                    } else if (e.getClick().isRightClick()) {
                        settingCycle.cycleDown();
                    }
                }
                buildInventory();
            }
        }
    }
}
