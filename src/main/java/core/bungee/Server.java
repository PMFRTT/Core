package core.bungee;

import org.bukkit.entity.Player;

public class Server {

    private final String name;
    private final String version;
    private final int port;

    public Server(String name, int port, String version) {
        this.name = name;
        this.version = version;
        this.port = port;
    }

    public void connect(Player player) {
        try {
            CoreBungeeCordClient.moveToServer(player, getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public int getPort() {
        return this.port;
    }

    public String getVersion() {
        return this.version;
    }
}
