package core.scoreboard;

import core.Utils;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreboardDisplay {

    final Plugin plugin;
    final Player player;
    final BukkitScheduler scheduler;

    private Scoreboard scoreboard;

    public ScoreboardDisplay(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.scheduler = this.plugin.getServer().getScheduler();
    }

    public void renderScoreboard() {
        if (needsUpdate()) {
            DebugSender.sendDebug(DebugType.GUI, "rendered sidelist", "Sidelist");
            org.bukkit.scoreboard.Scoreboard scoreboardBukkit = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
            Objective objective = scoreboardBukkit.registerNewObjective(plugin.getName(), "dummy", Utils.colorize(scoreboard.getTitle()));
            for (Score score : scoreboard.getScores()) {

                String prefix = "";

                if (score.getPrefix() != null) {
                    prefix = score.getPrefix();
                }

                String suffix = "";
                if (score.getSuffix() != null) {
                    suffix = score.getSuffix();
                }

                String fullString = Utils.colorize(prefix + score.getContent() + suffix);
                String cutString = fullString.substring(0, Math.min(fullString.length(), 37));
                if (fullString.length() > 38) {
                    cutString = cutString + "...";
                }

                org.bukkit.scoreboard.Score bukkitScore = objective.getScore(cutString);
                bukkitScore.setScore(score.getValue());
            }
            updateScoreboard();
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            player.setScoreboard(scoreboardBukkit);
        }
    }

    private Integer i = 0;
    private Integer j = 0;
    private String title = "";

    public void enableTitlesList(Integer cycleDuration) {
        if (scoreboard.getType() == ScoreboardType.MULTI_TITLE) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if (!scoreboard.getTitles().get(i).equals(title)) {
                    scoreboard.setTitle(scoreboard.getTitles().get(i));
                    title = scoreboard.getTitles().get(i);
                    i = scoreboard.getTitles().indexOf(title);
                }
                j++;
                renderScoreboard();
                if (j >= cycleDuration) {
                    i++;
                    j = 0;
                }
                if (i > scoreboard.getTitles().size() - 1) {
                    i = 0;
                }
            }, 0, 1);
        } else
            System.out.println("tried enabling multiline, when scoreboard is not of type multiline (core.scoreboard.ScoreboardsDisplay:62)");
    }

    public Integer getTitleIndex() {
        return i;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }


    private final List<String> scores = new ArrayList<>();

    private void updateScoreboard() {
        if (scoreboard != null) {
            scores.clear();
            for (Score score : scoreboard.getScores()) {
                scores.add(score.getFullContent());
            }
        }
    }

    private String currentTitle = "";

    private boolean needsUpdate() {
        if (scoreboard != null) {
            boolean returnValue = !currentTitle.equals(scoreboard.getTitles().get(i));
            if (!returnValue) {
                for (Score score : scoreboard.getScores()) {
                    if (!scores.contains(score.getFullContent())) {
                        returnValue = true;
                        break;
                    }
                }
            }
            currentTitle = scoreboard.getTitles().get(i);
            return returnValue;
        } else {
            return false;
        }
    }
}

