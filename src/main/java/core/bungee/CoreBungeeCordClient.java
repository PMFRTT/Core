package core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import core.core.CoreHandler;
import core.core.CoreMain;
import core.debug.DebugSender;
import core.debug.DebugType;
import core.sql.MySQLBungee;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@SuppressWarnings("UnstableApiUsage")
public class CoreBungeeCordClient{

    private static final String ADDRESS = "localhost";

    private static final MySQLBungee dataset = BungeeHandler.getDataset();
    private static final CoreMain main = CoreHandler.getMain();

    public static void init(){
        Bukkit.getMessenger().registerOutgoingPluginChannel(main, "BungeeCord");
    }

    public static void moveToServer(Player player, String serverName) {
        int port = dataset.getPort(serverName);
        if (isOnline(port)) {
            @SuppressWarnings("UnstableApiUsage") ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(main, "BungeeCord", out.toByteArray());
            DebugSender.sendDebug(DebugType.BUNGEE, player.getName() + " moved to server " + serverName);
        }
    }

    public static boolean isOnline(int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ADDRESS, port), 10);
            socket.close();
            DebugSender.sendDebug(DebugType.BUNGEE, "server on port " + port + " is online");
            return true;
        } catch (IOException e) {
            DebugSender.sendDebug(DebugType.BUNGEE, "server on port " + port + " could not be reached");
            return false;
        }
    }
}

