package core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.*;
import java.util.*;

public class CoreAccessPermissionFile {

    List<Player> playersWithPermissionFile = new ArrayList<Player>();
    CoreMain corePlugin;

    public CoreAccessPermissionFile(CoreMain corePlugin) {
        this.corePlugin = corePlugin;
    }

    public void readFile() {

        try {

            File dataFolder = corePlugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File permissions = new File(corePlugin.getDataFolder(), "permissions.yml");
            if (!permissions.exists()) {

                permissions.createNewFile();

            }

            BufferedReader reader = new BufferedReader(new FileReader(permissions));

            String currentLine;
            UUID uuid = null;
            Player player = null;
            PermissionAttachment attachment = null;

            while ((currentLine = reader.readLine()) != null) {

                if (currentLine.contains("uuid: ")) {
                    String uuidString = currentLine.replace("uuid: ", "");
                    uuid = UUID.fromString(uuidString);
                    player = Bukkit.getPlayer(uuid);
                }
                if (player != null) {
                    attachment = player.addAttachment(corePlugin);
                    playersWithPermissionFile.add(player);

                    if (currentLine.contains("  permission: ")) {
                        if (currentLine.startsWith(uuid + "  permission: core.canchangegamemode: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canchangegamemode: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canChangeGamemode", value);
                        } else if (currentLine.startsWith(uuid + "  permission: core.canchangeweather: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canchangeweather: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canChangeWeather", value);
                        } else if (currentLine.startsWith(uuid + "  permission: core.canchangetime: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canchangetime: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canChangeTime", value);
                        } else if (currentLine.startsWith(uuid + "  permission: core.canheal: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canheal: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canHeal", value);
                        } else if (currentLine.startsWith(uuid + "  permission: core.canchangedifficulty: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canchangedifficulty: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canChangeDifficulty", value);
                        } else if (currentLine.startsWith(uuid + "  permission: core.canshowtps: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canshowtps: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canShowTPS", value);
                        }
                        else if (currentLine.startsWith(uuid + "  permission: core.canseeinv: ")) {
                            String valueString = currentLine.replace(uuid + "  permission: core.canseeinv: ", "");
                            Boolean value = Boolean.valueOf(valueString);
                            attachment.setPermission("core.canSeeInv", value);
                        }
                    }
                    corePlugin.permissionAttachmentHashMap.put(uuid, attachment);

                }

            }


            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public static List<String> getPermissions(Map<String, Boolean> permissions) {
        List<String> permissionValues = new ArrayList<String>();
        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
            permissionValues.add("permission: " + entry.getKey() + ": " + entry.getValue());
        }
        return permissionValues;
    }

    public void createPermissionSave(Player player) {
        if (!playersWithPermissionFile.contains(player)) {
            try {
                File dataFolder = corePlugin.getDataFolder();
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }

                File permissionsFile = new File(corePlugin.getDataFolder(), "permissions.yml");
                if (!permissionsFile.exists()) {
                    permissionsFile.createNewFile();

                }

                FileWriter fw = new FileWriter(permissionsFile, true);
                @SuppressWarnings("resource")
                PrintWriter pw = new PrintWriter(fw);

                PermissionAttachment permissionAttachment = player.addAttachment(corePlugin);
                permissionAttachment.setPermission("core.canChangeGamemode", false);
                permissionAttachment.setPermission("core.canChangeWeather", false);
                permissionAttachment.setPermission("core.canChangeTime", false);
                permissionAttachment.setPermission("core.canHeal", false);
                permissionAttachment.setPermission("core.canChangeDifficulty", false);
                permissionAttachment.setPermission("core.canShowTPS", false);
                permissionAttachment.setPermission("core.canSeeInv", false);
                CoreMain.permissionAttachmentHashMap.put(player.getUniqueId(), permissionAttachment);

                Map<String, Boolean> permissions = permissionAttachment.getPermissions();

                List<String> permissionValues = getPermissions(permissions);
                int size = permissionValues.size();
                pw.println("uuid: " + player.getUniqueId());
                for (int i = 0; i < size; i++) {
                    pw.println(player.getUniqueId() + "  " + permissionValues.get(i));
                }
                pw.println("");
                pw.flush();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void test(Player player) {
        try {
            File dataFolder = corePlugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File permissionsFile = new File(corePlugin.getDataFolder(), "permissions.yml");
            if (!permissionsFile.exists()) {
                permissionsFile.createNewFile();

            }

            FileWriter fw = new FileWriter(permissionsFile, true);
            @SuppressWarnings("resource")
            PrintWriter pw = new PrintWriter(fw);
            BufferedReader reader = new BufferedReader(new FileReader(permissionsFile));
            List<String> lines = new ArrayList<String>();
            String currentline;
            while((currentline = reader.readLine()) != null){
                lines.add(currentline);
            }

            Map<String, Boolean> permissions = corePlugin.permissionAttachmentHashMap.get(player.getUniqueId()).getPermissions();

            List<String> permissionValues = getPermissions(permissions);
            int size = permissionValues.size();
            pw.println("uuid: " + player.getUniqueId());
            for (int i = 0; i < size; i++) {
                pw.println(player.getUniqueId() + "  " + permissionValues.get(i));
            }
            for(String s : lines){
                if(!s.contains(player.getUniqueId().toString())){
                    pw.println(s);
                }
            }
            pw.flush();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void removePlayer(UUID uuid) {
        try {
            File dataFolder = corePlugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File permissions = new File(corePlugin.getDataFolder(), "permissions.yml");
            if (!permissions.exists()) {
                permissions.createNewFile();

            }
            BufferedReader reader = new BufferedReader(new FileReader(permissions));

            BufferedWriter writer = new BufferedWriter(new FileWriter(permissions));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(uuid)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
