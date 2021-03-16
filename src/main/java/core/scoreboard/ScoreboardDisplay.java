package core.scoreboard;

import core.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.Objects;

public class ScoreboardDisplay {

    Scoreboard scoreboard;
    Scoreboard oldScoreboard;
    Plugin plugin;
    Player player;
    BukkitScheduler scheduler;

    public ScoreboardDisplay(Scoreboard scoreboard, Plugin plugin, Player player) {
        this.scoreboard = scoreboard;
        this.plugin = plugin;
        this.player = player;
        init();
    }

    private void init() {
        this.scheduler = this.plugin.getServer().getScheduler();
        renderScoreboard();
    }

    public void renderScoreboard() {

        this.scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                if(!scoreboard.equals(oldScoreboard)){
                    org.bukkit.scoreboard.Scoreboard scoreboardBukkit = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
                    Objective objective = scoreboardBukkit.registerNewObjective(plugin.getName(), "dummy", Utils.colorize(scoreboard.getTitle()));
                    for(Score score : scoreboard.getScores()){
                        org.bukkit.scoreboard.Score bukkitScore = objective.getScore(Utils.colorize(score.getContent()));
                        bukkitScore.setScore(score.getValue());
                    }
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    player.setScoreboard(scoreboardBukkit);
                    updateScoreboard(scoreboard);
                }else{
                    updateScoreboard(scoreboard);
                }
            }
        }, 0L, 1L);

    }

    public void updateScoreboard(Scoreboard scoreboard){
        this.oldScoreboard = this.scoreboard;
        this.scoreboard = scoreboard;
    }

}
