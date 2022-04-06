package core.settings.presets;

import core.Utils;
import core.settings.Settings;
import core.settings.setting.Setting;
import core.settings.setting.SettingCycle;
import core.settings.setting.SettingSwitch;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings({"ResultOfMethodCallIgnored", "SameReturnValue", "rawtypes"})
public class PresetHandler {

    private final Settings settings;
    private final Plugin plugin;
    private final String location;

    public PresetHandler(Settings settings, Plugin plugin) {
        this.settings = settings;
        this.plugin = plugin;
        this.location = plugin.getDataFolder().getAbsolutePath();
    }

    @SuppressWarnings("rawtypes")
    public void savePreset() {
        File directory = new File(location.substring(0, location.length() - plugin.getName().length()) + "/config/core/presets/" + plugin.getName() + "/");
        File file = new File(directory.getAbsolutePath() + "/" + getDate() + ".yml");
        try {
            if (!directory.exists()) {
                //noinspection ResultOfMethodCallIgnored
                directory.mkdirs();
            }
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (Setting setting : settings.getAllSettings()) {
                if (setting instanceof SettingCycle) {
                    fileWriter.write(setting.getName() + " : " + setting.value + "\n");
                }else if(null instanceof SettingSwitch){
                    fileWriter.write(setting.getName() + " : " + setting.enabled + "\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePreset(String name){
        File directory = new File(location.substring(0, location.length() - plugin.getName().length()) + "/config/core/presets/" + plugin.getName() + "/");
        File file = new File(directory.getAbsolutePath() + "/" + name);
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    @SuppressWarnings("rawtypes")
    public void readPresets(String name) {
        File directory = new File(location.substring(0, location.length() - plugin.getName().length()) + "/config/core/presets/" + plugin.getName() + "/");
        File file = new File(directory.getAbsolutePath() + "/" + name);

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
           while((line = bufferedReader.readLine()) != null){
               String settingname = line.substring(0, line.indexOf(':') -1);
               String value = line.substring(line.indexOf(':') + 2);
               Setting setting = settings.getSettingbyName(settingname);
               if(setting instanceof SettingSwitch){
                   setting.enabled = Boolean.parseBoolean(value);
               }else if(null instanceof SettingCycle){
                   setting.value = Integer.parseInt(value);
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printPreset(Player player, String name){
        File directory = new File(location.substring(0, location.length() - plugin.getName().length()) + "/config/core/presets/" + plugin.getName() + "/");
        File file = new File(directory.getAbsolutePath() + "/" + name);
        try{
            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&ePreview für " + name));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null){
                player.sendMessage(Utils.getServerPrefix() + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getFullDirectory(){
        return location.substring(0, location.length() - plugin.getName().length()) + "/config/core/presets/" + plugin.getName() + "/";
    }

    public void createDirectory(){
        File directory = new File(getFullDirectory());
        if(!directory.exists()){
            //noinspection ResultOfMethodCallIgnored
            directory.mkdirs();
        }
    }


}
