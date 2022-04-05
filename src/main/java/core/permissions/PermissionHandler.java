package core.permissions;

import core.sql.MySQLPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PermissionHandler {

    private static MySQLPermissions dataset;

    public static void init(){
        dataset = new MySQLPermissions();
        dataset.createTable();
        for(Player player : Bukkit.getOnlinePlayers()){
            PermissionConverter.generatePermissions(PermissionConverter.convertIntToBinary(getDataset().getPermissions(player.getUniqueId())));
        }
    }

    public static MySQLPermissions getDataset(){
        return PermissionHandler.dataset;
    }

}
