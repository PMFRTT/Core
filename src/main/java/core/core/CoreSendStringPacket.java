package core.core;

import core.Utils;
import core.debug.DebugSender;
import core.debug.DebugType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class CoreSendStringPacket {

    public static void sendPacketToHotbar(Player p, String text) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.colorize(text)));
    }

    @SuppressWarnings("deprecation")
    public static void sendPacketToTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle);
        DebugSender.sendDebug(DebugType.GUI, "title packet sent", "Title");
    }

}
