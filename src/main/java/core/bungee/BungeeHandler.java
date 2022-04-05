package core.bungee;

import core.sql.MySQLBungee;

public class BungeeHandler {

    private static MySQLBungee dataset;

    public static void init(){
        dataset = new MySQLBungee();
        dataset.createTable();
    }

    public static MySQLBungee getDataset() {
        return BungeeHandler.dataset;
    }
}
