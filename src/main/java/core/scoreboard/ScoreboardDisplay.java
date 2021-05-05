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

    public void renderScoreboard(Scoreboard scoreboard, String title) {
        org.bukkit.scoreboard.Scoreboard scoreboardBukkit = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboardBukkit.registerNewObjective(plugin.getName(), "dummy", Utils.colorize(title));
        for (Score score : scoreboard.getScores()) {
            String prefix = "";
            if (score.getPrefix() != null) {
                prefix = score.getPrefix();
            }
            String suffix = "";
            if (score.getSuffix() != null) {
                suffix = score.getSuffix();
            }
            org.bukkit.scoreboard.Score bukkitScore = objective.getScore(Utils.colorize(prefix + score.getContent() + suffix));
            bukkitScore.setScore(score.getValue());
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboardBukkit);
    }

    private Integer i = 0;

    public void enableTitlesList(Scoreboard scoreboard) {
        if (scoreboard.getType() == ScoreboardType.MULTI_TITLE) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                renderScoreboard(scoreboard, scoreboard.getTitles().get(i));
                i++;
                if (i > scoreboard.getTitles().size() - 1) {
                    i = 0;
                }
            }, 0, 240);
        }
        else System.err.println("tried enabling multiline, when scoreboard is not of type multiline (core.scoreboard.ScoreboardsDisplay:62)");
    }

}
