package core.chat;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEventHandler implements Listener {

    public ChatEventHandler(){

    }


    private static void getChatMessage(PlayerChatEvent e){
        e.setCancelled(true);


    }
}
