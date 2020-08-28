package core.chat;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChatColorHelper {

    public static HashMap<Player, String> playerColorMap = new HashMap<Player, String>();



    private static String getColor(Player player){

                return null;
    }

    private static void setColor(Player player, String color){
        playerColorMap.remove(player);
        playerColorMap.put(player, color);
    }

}
