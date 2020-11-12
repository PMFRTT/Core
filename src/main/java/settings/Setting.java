package settings;

public class Setting {

    private final String name;
    private final String description;
    private boolean enabled;


    public Setting(String name, String description, boolean enabled){
        this.name = name;
        this.description = description;
        this.enabled = enabled;
    }

    public void changeSettingValue(boolean enabled){
        this.enabled = enabled;
    }

    public boolean getSettingValue(){
        return this.enabled;
    }

}
