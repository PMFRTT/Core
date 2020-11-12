package core.core;

import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CoreSendStringPacket {

    public static void sendPacketToHotbar(Player p, String text) {
        ChatMessageType b = ChatMessageType.GAME_INFO;
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, b, p.getUniqueId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendPacketToChat(Player p, String text, org.bukkit.ChatColor color) {
        ChatMessageType b = ChatMessageType.CHAT;
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, b, p.getUniqueId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @SuppressWarnings("deprecation")
    public static void sendPacketToTitle(Player p, String title, String subtitle) {
        p.sendTitle(title, subtitle);
    }

}
