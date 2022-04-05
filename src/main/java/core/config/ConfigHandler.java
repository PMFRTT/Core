package core.config;

import core.sql.SQLConfig;

public class ConfigHandler {
    private static SQLConfig dataset;

    public static void init(){
        dataset = new SQLConfig();
        dataset.createTable();
    }

    public static SQLConfig getDataset(){
        return ConfigHandler.dataset;
    }

}
