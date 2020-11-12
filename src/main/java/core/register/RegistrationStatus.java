package core.register;

import core.Utils;
import core.core.CoreSendStringPacket;
import core.permissions.PermissionCard;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.Date;

public class RegistrationStatus {

    private final Player player; //registered Player

    private final int registrationLevel; //permissionLevel - unused
    private boolean registered; //true if players has successfully registered

    private final String registrationCode; //code for per player registration
    private final Date registrationTime; // time of registration - unused
    private final PermissionCard permissionCard; //stores all permission values


    public RegistrationStatus(Player player, boolean registered, String registrationCode, Date registrationTime, int registrationLevel) {
        this.player = player;
        this.registered = false;
        this.registrationCode = registrationCode;
        this.registrationTime = registrationTime;
        this.registrationLevel = registrationLevel;
        this.permissionCard = new PermissionCard(this.player, "0000000000");
        init();
    }

    private void init() {
        registered = confirmRegistration();
        if (registered) {
            permissionCard.convertToBooleanArray();
            CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&aRegistriert"), Utils.colorize("&fmit Code: " + "&a" + registrationCode));
        }else{
            System.out.println("nicht registriert");
        }
    }

    public boolean confirmRegistration(){

        if(Integer.parseInt(registrationCode) == convertStringToInt(player.getDisplayName())){
            System.out.println("confirmed");
            return true;
        }
        return false;
    }

    private int convertStringToInt(String s){
        s = StringUtils.upperCase(s);
        char[] chars = s.toCharArray();
        int returner = 1;
        for(char c : chars){
            int temp = c;
            int temp2 = 64;
            if(temp <= 90 && temp >= 65){
                returner *= (temp-temp2);
            }
        }
        System.out.println(returner);
        return returner;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }
}
