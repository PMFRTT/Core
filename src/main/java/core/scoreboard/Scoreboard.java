package core.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private final List<Score> scores = new ArrayList<Score>();
    private List<String> titles = new ArrayList<String>();

    private final Integer displayTimeMultiTitle;
    private final ScoreboardType type;

    public Scoreboard(ScoreboardType type, List<String> titles, Integer displayTimeMultiTitle) {
        this.type = type;
        this.titles = titles;
        this.displayTimeMultiTitle = displayTimeMultiTitle;
    }


    public void setTitle(List<String> titles) {
        switch (this.type) {
            case NO_TITLE, INVISIBLE:
                break;
            case STATIC_TITLE, SCROLLING_TITLE:
                this.titles.add(titles.get(0));
                break;
            case MULTI_TITLE:
                this.titles = titles;
        }
    }

    public void addScore(Score score) {
        this.scores.add(score);
    }

    public void removeScore(Score score) {
        this.scores.remove(score);
    }

    public List<Score> getScores() {
        return this.scores;
    }

    public ScoreboardType getType() {
        return this.type;
    }

    public Integer getDisplayTimeMultiTitle() {
        return this.displayTimeMultiTitle;
    }

    public String getTitle() {
        return this.titles.get(0);
    }

}
