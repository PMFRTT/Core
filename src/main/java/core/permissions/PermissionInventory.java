package core.permissions;

import core.Utils;
import core.core.CoreSendStringPacket;
import core.register.RegisterMain;
import core.register.RegistrationStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PermissionInventory {

    private final Player checker;
    private final Player checked;
    private final RegistrationStatus registrationStatus;
    private Inventory permissionInventory;

    public PermissionInventory(Player checker, Player checked) {
        this.checked = checked;
        this.checker = checker;
        this.registrationStatus = RegisterMain.getRegistrationStatus(checked);
        init();
    }

    private void init() {
        if (RegisterMain.checkRegistration(checked)) {
            createInventory();
            displayInventory();
        } else {
            CoreSendStringPacket.sendPacketToTitle(checker, Utils.colorize("Dieser Spieler ist nicht registriert!"), "");
        }
    }

    private void createInventory() {
        permissionInventory = Bukkit.createInventory(null, 27);

    }

    public void displayInventory() {
        checker.openInventory(permissionInventory);
    }

}
