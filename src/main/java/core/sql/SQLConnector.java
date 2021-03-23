package core.sql;

import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.SQLException;

public class SQLConnector {

    CoreMain plugin;

    public SQLConnector(CoreMain plugin){
        this.plugin = plugin;
    }

    private void run(){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(!plugin.SQL.isConnected()){
                    try{
                        plugin.SQL.connect();
                    }catch (SQLException | ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 20L);
    }


}
