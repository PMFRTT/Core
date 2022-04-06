package core.ranks;

import core.core.CoreHandler;
import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RankUpdater {

    private static final CoreMain coreMain = CoreHandler.getMain();
    private static final HashMap<String, Rank> playerRanks = new HashMap<>();


    public static void startUpdater() {
       updateAll();
       updater();
    }

    public static void updateAll(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }

    private static void updater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(coreMain, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                addPlayer(player);
            }
            DebugSender.sendDebug(DebugType.RANK, "updated, next update in 30");
        }, 0, 600L);
    }

    public static void addPlayer(Player player) {
        if (RankHandler.getDataset() != null) {
            if (containsPlayer(player)) {
                if (playerRanks.get(player.getDisplayName()) != Rank.convertIntToRank(RankHandler.getDataset().getRank(player.getUniqueId()))) {
                    playerRanks.put(player.getDisplayName(), Rank.convertIntToRank(RankHandler.getDataset().getRank(player.getUniqueId())));
                }
            } else {
                playerRanks.put(player.getDisplayName(), Rank.convertIntToRank(RankHandler.getDataset().getRank(player.getUniqueId())));
            }
        }else{
            playerRanks.put(player.getDisplayName(), Rank.DEV);
        }
    }

    public static boolean isDev(Player player) {
        if (containsPlayer(player)) {
            return playerRanks.get(player.getDisplayName()) == Rank.DEV;
        }
        return false;
    }

    private static boolean containsPlayer(Player player) {
        return playerRanks.containsKey(player.getDisplayName());
    }

    public static Rank getRank(Player player) {
        return playerRanks.get(player.getDisplayName());
    }

}
