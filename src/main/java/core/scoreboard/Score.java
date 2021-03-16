package core.scoreboard;

public class Score {

    private String content;
    int value;

    public Score(String content, Integer value) {
        this.content = content;
        this.value = value;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
