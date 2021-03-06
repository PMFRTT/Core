package core.ranks;

import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RankUpdater {

    private final CoreMain coreMain;
    private final HashMap<String, Rank> playerRanks = new HashMap<String, Rank>();

    public RankUpdater(CoreMain coreMain) {
        this.coreMain = coreMain;
    }

    public void startUpdater() {
       updateAll();
        updater();
    }

    public void updateAll(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }

    private void updater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(coreMain, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    addPlayer(player);
                }
                DebugSender.sendDebug(DebugType.RANK, "updated, next update in 30");
            }
        }, 0, 600L);
    }

    public void addPlayer(Player player) {
        if (CoreMain.mySQLRanks != null) {
            if (containsPlayer(player)) {
                if (playerRanks.get(player.getDisplayName()) != Rank.convertIntToRank(CoreMain.mySQLRanks.getRank(player.getUniqueId()))) {
                    playerRanks.put(player.getDisplayName(), Rank.convertIntToRank(CoreMain.mySQLRanks.getRank(player.getUniqueId())));
                }
            } else {
                playerRanks.put(player.getDisplayName(), Rank.convertIntToRank(CoreMain.mySQLRanks.getRank(player.getUniqueId())));
            }
        }else{
            playerRanks.put(player.getDisplayName(), Rank.DEV);
        }
    }

    public boolean isDev(Player player) {
        if (containsPlayer(player)) {
            return playerRanks.get(player.getDisplayName()) == Rank.DEV;
        }
        return false;
    }

    private boolean containsPlayer(Player player) {
        return playerRanks.containsKey(player.getDisplayName());
    }

    public Rank getRank(Player player) {
        return playerRanks.get(player.getDisplayName());
    }

}
