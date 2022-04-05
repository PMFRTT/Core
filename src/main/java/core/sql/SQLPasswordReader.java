package core.sql;

import core.core.CoreHandler;
import core.core.CoreMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SQLPasswordReader {

    private static final CoreMain main = CoreHandler.getMain();
    private static final String location = main.getDataFolder().getAbsolutePath();

    public static String readPassword(){
        File directory = new File(location.substring(0, location.length() - main.getName().length()) + "/config/core/");
        File file = new File(directory.getAbsolutePath() + "/password.txt");
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
