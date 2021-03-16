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

    Plugin plugin;
    Player player;
    BukkitScheduler scheduler;

    public ScoreboardDisplay(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        init();
    }

    private void init() {
        this.scheduler = this.plugin.getServer().getScheduler();
    }

    public void renderScoreboard(Scoreboard scoreboard) {
        org.bukkit.scoreboard.Scoreboard scoreboardBukkit = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboardBukkit.registerNewObjective(plugin.getName(), "dummy", Utils.colorize(scoreboard.getTitle()));
        for (Score score : scoreboard.getScores()) {
            org.bukkit.scoreboard.Score bukkitScore = objective.getScore(Utils.colorize(score.getContent()));
            bukkitScore.setScore(score.getValue());
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboardBukkit);
    }

}
