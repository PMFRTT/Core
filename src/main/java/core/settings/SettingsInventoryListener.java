package core.settings;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class SettingsInventoryListener implements Listener {

    private final Settings settings;
    private final Inventory inventory;

    public SettingsInventoryListener(Settings settings, Plugin plugin, Inventory inventory) {
        this.settings = settings;
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void getBackClick(InventoryClickEvent e) {

        int slot = e.getSlot();

        HumanEntity player = e.getWhoClicked();
        Inventory presetInventory = settings.getPresetInventory().getInventory();

        if (slot == 44) {
            player.closeInventory();
            player.openInventory(presetInventory);
        }
    }

    @EventHandler
    public void cancelClick(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();

        if (Objects.equals(e.getClickedInventory(), this.inventory)) {
            e.setCancelled(true);
        }
    }

    /*
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
         if (Objects.equals(e.getClickedInventory(), this.inventory)) {
            e.setCancelled(true);
            if (this.usableSlots.contains(e.getSlot())) {
                DebugSender.sendDebug(DebugType.SETTINGS, "setting clicked");
                if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.BARRIER)) {
                    e.getWhoClicked().openInventory(settings.getMasterSettings().getSettingsInventory().getInventory());
                } else if (getSettingfromSlot(e.getSlot()).getType().equals(SettingsType.SWITCH)) {
                    SettingSwitch settingSwitch = (SettingSwitch) getSettingfromSlot(e.getSlot());
                    if (e.getClick().isShiftClick()) {
                        if (settingSwitch.getSubSettings() != null) {
                            if (settingSwitch.getValue()) {
                                e.getWhoClicked().openInventory(settingSwitch.getSubSettings().getSettingsInventory().getInventory());
                            }
                        }
                    } else if (e.getClick().isLeftClick()) {
                        settingSwitch.changeSettingValue();
                    }
                } else if (getSettingfromSlot(e.getSlot()).getType().equals(SettingsType.CLICK)) {
                    SettingClick setting = (SettingClick) getSettingfromSlot(e.getSlot());

                    if (e.getClick().isShiftClick()) {
                        if (setting.getSubSettings() != null) {
                            e.getWhoClicked().openInventory(setting.getSubSettings().getSettingsInventory().getInventory());
                        }
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
    }*/

}
