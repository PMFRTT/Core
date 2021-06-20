package core.currency.invest;

import core.Utils;
import core.core.CoreMain;
import core.currency.Currency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvestingInventory implements Listener {

    private final Inventory inventory = Bukkit.createInventory(null, 45, "Trading");

    private final HashMap<Currency, Float> amountCache = new HashMap<Currency, Float>();

    Plugin plugin;
    Player player;

    public InvestingInventory(CoreMain plugin, Player player) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.player = player;
        updateCache();
    }

    public void updateInventory() {
        inventory.setItem(2, getPortfolioItem(Currency.USD));
        inventory.setItem(6, getFullPortfolioValue());

        int i = 28;
        for (Currency currency : Currency.values()) {
            if (currency != Currency.USD) {
                inventory.setItem(i, getPortfolioItem(currency));
                i++;
            }
        }


        inventory.setItem(19, getCurrencyItem(CurrencyPair.BTC_USD));
        inventory.setItem(20, getCurrencyItem(CurrencyPair.ETH_USD));
        inventory.setItem(21, getCurrencyItem(CurrencyPair.DOGE_USD));
        inventory.setItem(22, getCurrencyItem(CurrencyPair.ETC_USD));
        inventory.setItem(23, getCurrencyItem(CurrencyPair.LTC_USD));
        inventory.setItem(24, getCurrencyItem(CurrencyPair.ADA_USD));
        inventory.setItem(25, getCurrencyItem(CurrencyPair.ZEC_USD));

        for (int j = 0; j < 45; j++) {
            if (inventory.getItem(j) == null) {
                ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta emptyMeta = empty.getItemMeta();
                emptyMeta.setDisplayName("--");
                empty.setItemMeta(emptyMeta);
                inventory.setItem(j, empty);
            }
        }
    }

    public void updateCache() {
        for (Currency currency : Currency.values()) {
            amountCache.put(currency, CoreMain.mySQLMoney.getCurrency(player.getUniqueId(), currency));
        }
    }

    public HashMap<Currency, Float> getAmountCache() {
        return amountCache;
    }

    public Inventory getInventory() {
        updateInventory();
        return inventory;
    }

    private ItemStack getCurrencyItem(CurrencyPair currencyPair) {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(currencyPair.base.getDisplayName());

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&fAktueller Preis: " + core.currency.Utils.formatCurrencyString(ExchangeCore.getPrice(currencyPair), Currency.getFromCurrencyPair(currencyPair)) + "&f$↑"));
            add(Utils.colorize("&fHandelsvolumen (24h) : &e" + ExchangeCore.getVolume(currencyPair) + " &f" + currencyPair.base.getSymbol()));
            if (ExchangeCore.getChange(currencyPair) > 0.05) {
                add(Utils.colorize("&fVeränderung (24h) : &a" + ExchangeCore.getChange(currencyPair) + "&f%"));
            } else if (ExchangeCore.getChange(currencyPair) < -0.05) {
                add(Utils.colorize("&fVeränderung (24h) : &c" + ExchangeCore.getChange(currencyPair) + "&f%"));
            } else {
                add(Utils.colorize("&fVeränderung (24h) : &7" + ExchangeCore.getChange(currencyPair) + "&f%"));
            }

        }};

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack getPortfolioItem(Currency currency) {
        ItemStack itemStack = new ItemStack(Material.IRON_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Currency.getCurrencyName(currency));

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&FDu besitzt &a" + core.currency.Utils.formatCurrencyString(amountCache.get(currency), currency) + "&f " + Currency.getCurrencySymbol(currency)));
            if (currency != Currency.USD) {
                add(Utils.colorize("&fAktueller Wert: &a" + core.currency.Utils.formatCurrencyString(ExchangeCore.getPrice(Currency.getCurrencyPair(currency)) * InventoryList.getInvestingInventory(player).getAmountCache().get(currency), core.currency.Currency.BTC) + "&f $"));
            }
            add(Utils.colorize("&7Klicke hier um diesen Vermögenswert zu handeln!"));
        }};

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack getFullPortfolioValue() {
        ItemStack itemStack = new ItemStack(Material.ENDER_EYE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Utils.colorize("Portfolio (&a" + core.currency.Utils.formatCurrencyString(getTotalPortfolioValue(), Currency.USD) + "&f$)"));

        List<String> lore = new ArrayList<String>() {{
            for (Currency currency : Currency.values()) {
                if (amountCache.get(currency) != 0) {
                    if (currency != Currency.USD) {
                        add(Utils.colorize("&FDu besitzt &a" + core.currency.Utils.formatCurrencyString(amountCache.get(currency), currency) + "&f " + Currency.getCurrencySymbol(currency) + " (&a" + core.currency.Utils.formatCurrencyString(ExchangeCore.getPrice(Currency.getCurrencyPair(currency)) * InventoryList.getInvestingInventory(player).getAmountCache().get(currency), core.currency.Currency.USD) + "&f$)"));
                    }
                }
            }
        }};

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == inventory) {
            if (e.getCurrentItem().getType().equals(Material.IRON_INGOT)) {
                InventoryList.getCurrencyInventory(player).setCurrency(Currency.getCurrency(e.getCurrentItem().getItemMeta().getDisplayName()));
                player.openInventory(new CurrencyInventory(plugin, player).getInventory());
            }
            e.setCancelled(true);
        }
    }

    public float getTotalPortfolioValue() {
        float total = 0;
        for (Currency currency : Currency.values()) {
            if (amountCache.get(currency) != 0) {
                if (currency != Currency.USD) {
                    total += amountCache.get(currency) * ExchangeCore.getPrice(Currency.getCurrencyPair(currency));
                }
            }
        }
        total +=amountCache.get(Currency.USD);
        return total;
    }

}
