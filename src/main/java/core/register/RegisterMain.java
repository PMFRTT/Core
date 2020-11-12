package core.register;

import core.Utils;
import core.core.CoreSendStringPacket;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;

public class RegisterMain {

    private static final HashMap<Player, RegistrationStatus> registeredPlayers = new HashMap<>();


    public static void registerPlayer(Player player, String registrationCode) {
        if (!registeredPlayers.containsKey(player)) {
            Date date = new Date();
            RegistrationStatus registrationStatus;
            registrationStatus = new RegistrationStatus(player, true, registrationCode, date, 1);
            registeredPlayers.put(player, registrationStatus);
        } else {
            CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&cDu bist bereits registriert"), Utils.colorize("mit Code: " + registeredPlayers.get(player).getRegistrationCode()));
        }
    }

    public static void unregisterPlayer(Player player) {
        registeredPlayers.remove(player);
    }

    public static boolean checkRegistration(Player player) {
        if (registeredPlayers.containsKey(player)) {
            return registeredPlayers.get(player).confirmRegistration();
        } else {
            return false;
        }
    }

    public static RegistrationStatus getRegistrationStatus(Player player){
        if (checkRegistration(player)) {
            return registeredPlayers.get(player);
        }else{
            return null;
        }
    }

}
