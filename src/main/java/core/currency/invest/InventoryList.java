package core.currency.invest;

import core.core.CoreMain;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class InventoryList {

    public static HashMap<String, InvestingInventory> investingInventoryHashMap = new HashMap<String, InvestingInventory>();
    public static HashMap<String, CurrencyInventory> currencyInventoryHashMap = new HashMap<String, CurrencyInventory>();

    private static Plugin plugin;

    public InventoryList(Plugin plugin) {
        InventoryList.plugin = plugin;
    }

    public static CurrencyInventory getCurrencyInventory(Player player) {
        if (!currencyInventoryHashMap.containsKey(player.getDisplayName())) {
            currencyInventoryHashMap.put(player.getDisplayName(), new CurrencyInventory( InventoryList.plugin, player));
        }
        return currencyInventoryHashMap.get(player.getDisplayName());
    }

    public static InvestingInventory getInvestingInventory(Player player) {
        if (!investingInventoryHashMap.containsKey(player.getDisplayName())) {
            investingInventoryHashMap.put(player.getDisplayName(), new InvestingInventory((CoreMain) InventoryList.plugin, player));
        }
        return investingInventoryHashMap.get(player.getDisplayName());
    }

}
