package core.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public static ItemStack createItemStack(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static ItemStack createItemStack(Material material, int amount, String name) {
        ItemStack itemStack = createItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore) {
        ItemStack itemStack = createItemStack(material, amount, name);
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static void hideEnchants(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
    }

    public static void enchant(ItemStack itemStack, boolean value) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        if (value) {
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        } else {
            itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        itemStack.setItemMeta(itemMeta);
    }

    public static void addLore(ItemStack itemStack, String string) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        List<String> existingLore;
        if (itemMeta.getLore() != null) {
            existingLore = itemMeta.getLore();
        } else {
            existingLore = new ArrayList<String>();
        }
        existingLore.add(string);
        itemMeta.setLore(existingLore);
        itemStack.setItemMeta(itemMeta);
    }

    public static void addLore(ItemStack itemStack, List<String> strings) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        List<String> existingLore;
        if (itemMeta.getLore() != null) {
            existingLore = itemMeta.getLore();
        } else {
            existingLore = new ArrayList<String>();
        }
        existingLore.addAll(strings);
        itemMeta.setLore(existingLore);
        itemStack.setItemMeta(itemMeta);
    }

}
