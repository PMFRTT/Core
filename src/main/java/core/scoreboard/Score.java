package core.scoreboard;

public class Score {

    private String content;
    private String prefix;
    private String suffix;
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

    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix(){
        return this.prefix;
    }

    public void setSuffix(String suffix){
        this.suffix = suffix;
    }

    public String getSuffix(){
        return this.suffix;
    }

}
