package core;

public class CoreSendStringPacket {

    /**public static void sendPacketToHotbar(Player p, String text) {
        ChatMessageType b = ChatMessageType.GAME_INFO;
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), b);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendPacketToChat(Player p, String text, org.bukkit.ChatColor color) {
        ChatMessageType b = ChatMessageType.CHAT;
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + color + text + "\"}"), b);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @SuppressWarnings("deprecation")
    public static void sendPacketToTitle(Player p, String title, String subtitle) {
        p.sendTitle(title, subtitle);

    }*/

}
