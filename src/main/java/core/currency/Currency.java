package core.currency;

//import org.knowm.xchange.currency.CurrencyPair;

public enum Currency {
    /*
    USD, BTC, ETH, DOGE, ETC, LTC, ADA, ZEC;

    public static String getCurrencyName(Currency currency) {
        return currency.toString();
    }

    public static Currency getCurrency(String name) {
        for (Currency currency : Currency.values()) {
            if (currency.toString().equalsIgnoreCase(name)) {
                return currency;
            }
        }
        return null;
    }

    public static Currency getFromCurrencyPair(CurrencyPair currencyPair) {
        String currencyNameFromPair = currencyPair.base.getDisplayName();
        for (Currency currency : Currency.values()) {
            if (getCurrencyName(currency).equalsIgnoreCase(currencyNameFromPair)) {
                return currency;
            }
        }
        return null;
    }

    public static CurrencyPair getCurrencyPair(Currency currency) {
        return switch (currency) {
            case BTC -> CurrencyPair.BTC_USD;
            case ETH -> CurrencyPair.ETH_USD;
            case DOGE -> CurrencyPair.DOGE_USD;
            case ETC -> CurrencyPair.ETC_USD;
            case LTC -> CurrencyPair.LTC_USD;
            case ZEC -> CurrencyPair.ZEC_USD;
            case ADA -> CurrencyPair.ADA_USD;
            default -> null;
        };
    }

    public static String getCurrencySymbol(Currency currency) {
        if (currency == Currency.USD) {
            return "$";
        }
        return getCurrencyName(currency);
    }
*/
}
