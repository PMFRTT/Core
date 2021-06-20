package core.currency.invest;

import info.bitrich.xchangestream.coinbasepro.CoinbaseProStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.ProductSubscription.ProductSubscriptionBuilder;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.bukkit.plugin.Plugin;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ExchangeCore {

    private static StreamingExchange exchange = null;
    public static boolean enabled = false;

    private static final List<CurrencyPair> allCurrencyPairs = new ArrayList<CurrencyPair>() {{
        add(CurrencyPair.BTC_USD);
        add(CurrencyPair.ETH_USD);
        add(CurrencyPair.DOGE_USD);
        add(CurrencyPair.ETC_USD);
        add(CurrencyPair.LTC_USD);
        add(CurrencyPair.ADA_USD);
        add(CurrencyPair.XMR_USD);
        add(CurrencyPair.EUR_USD);
        add(CurrencyPair.ZEC_USD);
    }};

    private static final HashMap<CurrencyPair, Float> prices = new HashMap<CurrencyPair, Float>() {{
        for (CurrencyPair currencyPair : allCurrencyPairs) {
            put(currencyPair, 0f);
        }
    }};

    private static final HashMap<CurrencyPair, Float> volumes = new HashMap<CurrencyPair, Float>() {{
        for (CurrencyPair currencyPair : allCurrencyPairs) {
            put(currencyPair, 0f);
        }
    }};

    private static final HashMap<CurrencyPair, Float> changes = new HashMap<CurrencyPair, Float>() {{
        for (CurrencyPair currencyPair : allCurrencyPairs) {
            put(currencyPair, 0f);
        }
    }};

    public static void connect(Plugin plugin) {

        if (Arrays.asList(plugin.getServer().getPluginManager().getPlugins()).contains(plugin.getServer().getPluginManager().getPlugin("Lobby"))) {
            enabled = true;
        }

        if (enabled) {
            exchange = StreamingExchangeFactory.INSTANCE.createExchange(CoinbaseProStreamingExchange.class.getName());
            ProductSubscriptionBuilder productSubscriptionBuilder = ProductSubscription.create();
            for (CurrencyPair currencyPair : allCurrencyPairs) {
                productSubscriptionBuilder.addTicker(currencyPair).addTrades(currencyPair);
            }
            exchange.connect(productSubscriptionBuilder.build()).blockingAwait();
            createTickers();
        }
    }

    private static void createTickers() {
        for (CurrencyPair currencyPair : allCurrencyPairs) {
            Disposable trades = exchange.getStreamingMarketDataService()
                    .getTrades(currencyPair)
                    .subscribe(trade -> {

                        prices.put(currencyPair, Float.parseFloat(trade.getPrice().toString()));

                    });
            Disposable ticker = exchange.getStreamingMarketDataService()
                    .getTicker(currencyPair)
                    .subscribe(trade -> {

                        volumes.put(currencyPair, Float.parseFloat(trade.getVolume().toString()));
                        float changePercentage = (Float.parseFloat(trade.getOpen().toString()) / prices.get(currencyPair) - 1) * -100;
                        changes.put(currencyPair, changePercentage);

                    });

        }
    }


    public static float getPrice(CurrencyPair currencyPair) {
        if (prices.get(currencyPair) != null) {
            return prices.get(currencyPair);
        }
        return 0;
    }

    public static float getVolume(CurrencyPair currencyPair) {
        if (volumes.get(currencyPair) != null) {
            return volumes.get(currencyPair);
        }
        return 0;
    }

    public static float getChange(CurrencyPair currencyPair) {
        if (changes.get(currencyPair) != null) {
            return changes.get(currencyPair);
        }
        return 0;
    }

}
