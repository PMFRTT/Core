package core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class CoreBungeeCordClient implements PluginMessageListener {

    static CoreMain corePlugin;
    private static final HashMap<String, Integer> serverPorts = new HashMap<String, Integer>();
    private static final HashMap<Integer, String> portServer = new HashMap<Integer, String>();
    private static final String ADDRESS = "192.168.178.97";
    static byte[] message;
    public static int playerCount;

    public CoreBungeeCordClient(CoreMain corePlugin) {
        CoreBungeeCordClient.corePlugin = corePlugin;
        Bukkit.getMessenger().registerOutgoingPluginChannel(corePlugin, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(corePlugin, "BungeeCord", this);
    }

    public static void loadServers() {
        try {

            File dataFolder = corePlugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File servers = new File(corePlugin.getDataFolder(), "servers.yml");
            if (!servers.exists()) {

                servers.createNewFile();

            }

            Scanner scanner = new Scanner(new FileReader(servers));

            while (scanner.hasNext()) {

                String line = scanner.nextLine();
                int endName = line.indexOf(" ");
                int endPort = line.indexOf(" ", endName + 2);

                String name = line.substring(5, endName);
                int port = Integer.parseInt(line.substring(endName + 6, endPort));

                serverPorts.put(name, port);
                portServer.put(port, name);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveToServer(Player player, String serverName) {
        loadServers();
        if (serverPorts.containsKey(serverName)) {
            if (isOnline(serverPorts.get(serverName))) {
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
        } else {
            player.sendMessage(Utils.getServerPrefix() + Utils.colorize("&cDer Server &4" + serverName + "&c ist nicht eingetragen! Bitte wende dich an einen Admin!"));
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

    public static void getPlayerAmount(int port, Player player) {
        String serverName = portServer.get(port);
        System.out.println(serverName);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(serverName);
        player.sendPluginMessage(corePlugin, "BungeeCord", out.toByteArray());
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (message != null) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subchannel = in.readUTF();
            if (subchannel.equals("PlayerCount")) {
                playerCount = in.readInt();
            }
        } else {
            playerCount = 0;
        }
    }
}

