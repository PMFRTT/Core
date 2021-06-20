package core.sql;

import core.core.CoreMain;
import core.currency.Currency;
import core.debug.DebugSender;
import core.debug.DebugType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLCurrency {


    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS CURRENCY " + "(NAME VARCHAR(100), UUID VARCHAR(100), USD FLOAT(16), BTC FLOAT(16), ETH FLOAT(16), DOGE FLOAT(16), ETC FLOAT(16), LTC FLOAT(16), ADA FLOAT(16), ZEC FLOAT(16), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Currency");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            String name = player.getName();
            UUID uuid = player.getUniqueId();
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM CURRENCY WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!exists(uuid)) {
                PreparedStatement preparedStatement1 = CoreMain.SQL.getConnection().prepareStatement("INSERT IGNORE INTO CURRENCY (NAME,UUID) VALUES (?,?)");
                preparedStatement1.setString(1, name);
                preparedStatement1.setString(2, uuid.toString());
                preparedStatement1.executeUpdate();
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (create)", "Currency");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT * FROM CURRENCY WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (exists)", "Currency");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setCurrency(UUID uuid, Currency currency, double amount) {
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("UPDATE CURRENCY SET " + Currency.getCurrencyName(currency) + "=? WHERE UUID=?");
            preparedStatement.setFloat(1, (float) amount);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (set)", "Currency");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getCurrency(UUID uuid, Currency currency) {
        float amount = 0;
        try {
            PreparedStatement preparedStatement = CoreMain.SQL.getConnection().prepareStatement("SELECT " + currency.toString() + " FROM CURRENCY WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getFloat(Currency.getCurrencyName(currency));
            }
            DebugSender.sendDebug(DebugType.DATABASE, "database was accessed (get)", "Currency");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    public void addCurrency(Player player, double amount, Currency currency) {
        setCurrency(player.getUniqueId(), currency, getCurrency(player.getUniqueId(), currency) + amount);
    }

    public boolean convertCurrencies(Player player, Currency currencyfrom, double amountfrom, Currency currencyto, double amountto){
        if(getCurrency(player.getUniqueId(), currencyfrom) >= amountfrom){
            addCurrency(player, -amountfrom, currencyfrom);
            addCurrency(player, amountto, currencyto);
            return true;
        }
        else return false;
    }

}
