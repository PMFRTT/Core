package core.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import core.Utils;
import core.core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CoreBungeeCordClient implements PluginMessageListener {

    static CoreMain corePlugin;
    private static final String ADDRESS = "localhost";
    public static int playerCount;

    public CoreBungeeCordClient(CoreMain corePlugin) {
        CoreBungeeCordClient.corePlugin = corePlugin;
        Bukkit.getMessenger().registerOutgoingPluginChannel(corePlugin, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(corePlugin, "BungeeCord", this);
    }

    public static void moveToServer(Player player, String serverName) {
        if (isOnline(Integer.parseInt(CoreMain.mySQLBungee.getServer(serverName.toUpperCase())))) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(corePlugin, "BungeeCord", out.toByteArray());
        } else {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Fallback");
            player.sendPluginMessage(corePlugin, "BungeeCord", out.toByteArray());
            player.teleport(new Location(Bukkit.getWorld("world"), -40, 21, 88));
            player.sendMessage(Utils.getPrefix("BungeeCord") + Utils.colorize("&cServer ist Offline!"));
        }
    }

    public static boolean isOnline(int port) {
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ADDRESS, port), 10);
            s.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void getPlayerAmount(String serverName, Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(serverName);
        player.sendPluginMessage(corePlugin, "BungeeCord", out.toByteArray());
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        try {
            if (message != null) {
                ByteArrayDataInput in = ByteStreams.newDataInput(message);
                String subchannel = in.readUTF();
                if (subchannel.equals("PlayerCount")) {
                    String server = in.readUTF();
                    playerCount = in.readInt();
                }
            }
        } catch (Exception ignored) {
        }
    }
}

