package core.ranks;

import core.sql.MySQLRanks;

public class RankHandler {

    private static MySQLRanks dataset;

    public static void init(){
        dataset = new MySQLRanks();
        dataset.createTable();
    }

    public static MySQLRanks getDataset(){
        return RankHandler.dataset;
    }

}
