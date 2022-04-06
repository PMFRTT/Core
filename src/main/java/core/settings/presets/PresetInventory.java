package core.settings.presets;

import core.Utils;
import core.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PresetInventory implements Listener {

    final Plugin plugin;
    final Settings settings;
    final Inventory inventory;

    public PresetInventory(Plugin plugin, Settings settings) {
        this.plugin = plugin;
        this.settings = settings;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
        inventory = Bukkit.createInventory(null, 45, "Preset-Einstellungen");
    }

    public Inventory getInventory() {
        updateInventory();
        return inventory;
    }

    public void updateInventory() {
        addBackButton(inventory);
        addSaveButton(inventory);
        addRefreshButton(inventory);
        int i = 0;
        for (File file : getAllPresets(settings.getPresetHandler())) {
            ItemStack itemStack = new ItemStack(Material.COMMAND_BLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            Objects.requireNonNull(itemMeta).setDisplayName(file.getName());
            ArrayList<String> lore = new ArrayList<>() {{
                add(Utils.colorize("&7Linksklick: &aLade&7 dieses Preset"));
                add(Utils.colorize("&7Shift + Linksklick: &cLösche&7 dieses Preset"));
                add(Utils.colorize("&7Mittlere Maustaste: Erhalte eine &ePreview"));

            }};
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(i, itemStack);
            i++;
        }
        for (int j = 0; j < 44; j++) {
            if (inventory.getItem(j) == null) {
                ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta emptyMeta = empty.getItemMeta();
                Objects.requireNonNull(emptyMeta).setDisplayName("--");
                empty.setItemMeta(emptyMeta);
                inventory.setItem(j, empty);
            }
        }
    }

    private void addBackButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(Utils.colorize("&4Zurück"));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(44, itemStack);
    }

    private void addSaveButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(Utils.colorize("&aSpeichern"));
        ArrayList<String> lore = new ArrayList<>() {{
            add(Utils.colorize("&aSpeichere&7 deine aktuellen"));
            add(Utils.colorize("&7Einstellungen als Preset ab!"));
        }};
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(43, itemStack);
    }

    private void addRefreshButton(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.HONEYCOMB);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(Utils.colorize("&eAktualisieren"));
        ArrayList<String> lore = new ArrayList<>() {{
            add(Utils.colorize("&7Werden nicht alle Presets angezeigt?"));
            add(Utils.colorize("&7Hier kannst du alle Presets &eaktualisieren&7!"));
        }};
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(42, itemStack);
    }

    private List<File> getAllPresets(PresetHandler presetHandler) {

        List<File> allFiles;

        File directory = new File(presetHandler.getFullDirectory());
        File[] files = directory.listFiles();

        allFiles = Arrays.asList(Objects.requireNonNull(files));
        return allFiles;

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.getClickedInventory() == this.inventory) {
            if (e.getSlot() == 44 && Objects.requireNonNull(e.getCurrentItem()).getType() == Material.BARRIER && Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName().equals(Utils.colorize("&4Zurück"))) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(settings.getMasterSettings().getSettingsInventory().getInventory());
            } else if (e.getSlot() == 43 && Objects.requireNonNull(e.getCurrentItem()).getType() == Material.BOOK) {
                settings.getPresetHandler().savePreset();
                updateInventory();
            } else if (e.getSlot() == 42 && Objects.requireNonNull(e.getCurrentItem()).getType() == Material.HONEYCOMB) {
                inventory.clear();
                updateInventory();
            } else if (Objects.requireNonNull(e.getCurrentItem()).getType() == Material.COMMAND_BLOCK) {
                if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                    settings.getPresetHandler().deletePreset(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName());
                    inventory.setItem(getAllPresets(settings.getPresetHandler()).size(), null);
                    inventory.setItem(e.getSlot(), null);
                    updateInventory();
                } else if (e.getClick().isLeftClick()) {
                    settings.getPresetHandler().readPresets(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName());
                } else if (e.getClick().equals(ClickType.MIDDLE)) {
                    e.getWhoClicked().closeInventory();
                    settings.getPresetHandler().printPreset((Player) e.getWhoClicked(), Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName());
                }
            }
            e.setCancelled(true);
        }
    }
}
