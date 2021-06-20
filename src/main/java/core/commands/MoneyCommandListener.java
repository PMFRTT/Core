package core.commands;

import core.Utils;
import core.core.CoreMain;
import core.currency.Currency;
import core.currency.invest.InventoryList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoneyCommandListener implements CommandExecutor {

    private final CoreMain plugin;

    public MoneyCommandListener(CoreMain plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        }

        if(command.getLabel().equalsIgnoreCase("money")){
            if(args.length == 0){
                player.sendMessage(Utils.getPrefix("Geld") + Utils.colorize("Du hast &a" + core.currency.Utils.formatCurrencyString(CoreMain.mySQLMoney.getCurrency(player.getUniqueId(), Currency.USD), Currency.USD) + "€!"));
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("invest")){
                    player.openInventory(InventoryList.getInvestingInventory(player).getInventory());
                }
            }
        }

        return false;
    }
}
