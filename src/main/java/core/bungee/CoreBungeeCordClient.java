package core.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.Bukkit;
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
        int port = CoreMain.mySQLBungee.getPort(serverName);
        if (isOnline(port)) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(corePlugin, "BungeeCord", out.toByteArray());
            DebugSender.sendDebug(DebugType.BUNGEE, player.getName() + " moved to server " + serverName);
        }
    }

    public static boolean isOnline(int port) {
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ADDRESS, port), 10);
            s.close();
            DebugSender.sendDebug(DebugType.BUNGEE, "server on port " + port + " is online");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            DebugSender.sendDebug(DebugType.BUNGEE, "server on port " + port + " could not be reached");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

