package core.core;

import core.Utils;
import core.bungee.BungeeHandler;
import core.bungee.CoreBungeeCordClient;
import core.commands.MainCommandListener;
import core.config.ConfigHandler;
import core.debug.DebugSender;
import core.debug.DebugType;
import core.hotbar.HotbarManager;
import core.permissions.PermissionHandler;
import core.ranks.RankHandler;
import core.ranks.RankUpdater;
import core.sql.MySQL;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class CoreHandler {

    private static CoreMain main;
    private static MySQL SQL;

    private static JavaPlugin guestPlugin;

    public static void init(CoreMain main){
        CoreHandler.main = main;
        CoreHandler.SQL = new MySQL();
        startSQLConnection();
        createDatasets();
        createCommandListeners();
        startEventHandlers();
        startServerInfoCollector();
        startRankUpdater();
        startHotbarManager();
        startBungeeClient();
        configureGamerules();

        DebugSender.sendDebug(DebugType.PLUGIN, "core loaded", "Core");
    }

    public static CoreMain getMain(){
        return CoreHandler.main;
    }

    private static void createCommandListeners(){
        MainCommandListener commandListener = new MainCommandListener();
        commandListener.init();
    }

    private static void createDatasets(){
        ConfigHandler.init();
        PermissionHandler.init();
        BungeeHandler.init();
        RankHandler.init();
    }

    private static void startEventHandlers(){
        EventHandler eventHandler = new EventHandler();
        eventHandler.startEventHandlers();
    }

    private static void startServerInfoCollector(){
        CoreServerInfo.startUpdater();
    }

    private static void startRankUpdater(){
        RankUpdater.startUpdater();
    }

    private static void startHotbarManager(){
        HotbarManager.init();
    }

    private static void startBungeeClient(){
        CoreBungeeCordClient.init();
    }

    public static void startSQLConnection(){
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException ignored) {

        }
    }

    public static void endSQLConnection(){
        try {
            SQL.disconnect();
        } catch (SQLException ignored){

        }
    }

    public static MySQL getSQL(){
        return CoreHandler.SQL;
    }

    private static void configureGamerules(){
        Utils.changeGamerule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
    }

    public static void setGuestPlugin(JavaPlugin guestPlugin){
        CoreHandler.guestPlugin = guestPlugin;
    }

    public static JavaPlugin getGuestPlugin(){
        if(CoreHandler.guestPlugin != null){
            return CoreHandler.guestPlugin;
        }
        else {
            return CoreHandler.getMain();
        }
    }

}
