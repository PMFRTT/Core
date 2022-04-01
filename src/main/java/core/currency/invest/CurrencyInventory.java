package core.currency.invest;

import org.bukkit.event.Listener;

public class CurrencyInventory implements Listener {
/*
    private final Plugin plugin;
    private final Player player;
    private Currency currency = Currency.ETH;
    private final Inventory inventory = Bukkit.createInventory(null, 54);

    HashMap<Integer, Integer> values = new HashMap<Integer, Integer>();

    private static float amount;

    public CurrencyInventory(Plugin plugin, Player player) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.player = player;
        setUpValues();
        update();
        updater();
    }

    public void update() {

        inventory.setItem(4, getPortfolioItem(Currency.USD));

        inventory.setItem(28, getCurrencyItem());
        inventory.setItem(29, getPortfolioItem(currency));

        inventory.setItem(32, getBuyItem());
        inventory.setItem(34, getSellItem());

        inventory.setItem(41, getMaxBuyItem());
        inventory.setItem(43, getMaxSellItem());

        int adder = 9;

        for (Integer i : values.keySet()) {
            if (i == 4) {
                adder = 10;
            }
            inventory.setItem(i + adder, getSkull(values.get(i)));
        }


        addBackButton(this.inventory, 45);

        for (int j = 0; j < 54; j++) {
            if (inventory.getItem(j) == null) {
                ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta emptyMeta = empty.getItemMeta();
                emptyMeta.setDisplayName("--");
                empty.setItemMeta(emptyMeta);
                inventory.setItem(j, empty);
            }
        }

    }

    private void setUpValues() {
        for (int i = 0; i < 8; i++) {
            values.put(i, 0);
        }
    }


    public ItemStack getBuyItem() {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Utils.colorize("&aKaufen"));

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&fDu erhältst &a" + core.currency.Utils.formatCurrencyString(calculateTotal(), currency) + "&f " + Currency.getCurrencyName(currency)));
            add(Utils.colorize("&fDu zahlst &a" + core.currency.Utils.formatCurrencyString(calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)), currency) + "&f " + Currency.getCurrencySymbol(Currency.USD)));
        }};

        if (calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)) > InventoryList.getInvestingInventory(player).getAmountCache().get(Currency.USD)) {
            itemStack.setType(Material.GRAY_STAINED_GLASS_PANE);
            itemMeta.setDisplayName(Utils.colorize("&eNicht genügend USD"));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getMaxBuyItem() {
        ItemStack itemStack = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Utils.colorize("&aMaximum Kaufen"));

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&fDu erhältst &a" + core.currency.Utils.formatCurrencyString(getMaxBuy(currency), currency) + "&f " + Currency.getCurrencyName(currency)));
            add(Utils.colorize("&fDu zahlst &a" + core.currency.Utils.formatCurrencyString(getMaxBuy(currency) * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)), currency) + "&f " + Currency.getCurrencySymbol(Currency.USD)));
        }};

        if (getMaxBuy(currency) < 0.001) {
            itemStack.setType(Material.GRAY_WOOL);
            itemMeta.setDisplayName(Utils.colorize("&eZu geringe Menge"));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getSellItem() {
        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Utils.colorize("&aVerkaufen"));

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&fDu erhältst &a" + core.currency.Utils.formatCurrencyString(calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)), currency) + "&f " + Currency.getCurrencySymbol(Currency.USD)));
            add(Utils.colorize("&fDu zahlst &a" + core.currency.Utils.formatCurrencyString(calculateTotal(), currency) + "&f " + Currency.getCurrencyName(currency)));
        }};

        if (calculateTotal() > InventoryList.getInvestingInventory(player).getAmountCache().get(currency)) {
            itemStack.setType(Material.GRAY_STAINED_GLASS_PANE);
            itemMeta.setDisplayName(Utils.colorize("&eNicht genügend " + Currency.getCurrencySymbol(currency)));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getMaxSellItem() {
        ItemStack itemStack = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Utils.colorize("&aMaximum Verkaufen"));

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&fDu erhältst &a" + core.currency.Utils.formatCurrencyString(getMaxSell(currency) * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)), currency) + "&f " + Currency.getCurrencySymbol(Currency.USD)));
            add(Utils.colorize("&fDu zahlst &a" + core.currency.Utils.formatCurrencyString(getMaxSell(currency), currency) + "&f " + Currency.getCurrencyName(currency)));
        }};

        if (getMaxSell(currency) < 0.001) {
            itemStack.setType(Material.GRAY_WOOL);
            itemMeta.setDisplayName(Utils.colorize("&eZu geringe Menge"));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public static ItemStack getSkull(int value) {

        String url = core.currency.Utils.getSkullURL(value);

        ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        if (url.isEmpty()) return item;


        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        update();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private ItemStack getCurrencyItem() {
        ItemStack itemStack = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();

        CurrencyPair currencyPair = Currency.getCurrencyPair(currency);

        itemMeta.setDisplayName(currencyPair.base.getDisplayName());

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&fAktueller Preis: " + core.currency.Utils.formatCurrencyString(ExchangeCore.getPrice(currencyPair), Currency.getFromCurrencyPair(currencyPair)) + "&f$↑"));
            add(Utils.colorize("&fHandelsvolumen (24h) : &e" + ExchangeCore.getVolume(currencyPair) + " &f" + currencyPair.base.getSymbol()));
            if (ExchangeCore.getChange(currencyPair) > 0.05) {
                add(Utils.colorize("&fVeränderung (24h) : &a" + ExchangeCore.getChange(currencyPair) + "&f%"));
            } else if (ExchangeCore.getChange(currencyPair) < -0.05) {
                add(Utils.colorize("&fVeränderung (24h) : &c" + ExchangeCore.getChange(currencyPair) + "&f%"));
            } else {
                add(Utils.colorize("&fVeränderung (24h) : &7" + ExchangeCore.getChange(currencyPair) + "&f%"));
            }

        }};

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack getPortfolioItem(Currency currency) {
        ItemStack itemStack = new ItemStack(Material.IRON_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Currency.getCurrencyName(currency));

        List<String> lore = new ArrayList<String>() {{
            add(Utils.colorize("&FDu besitzt &a" + core.currency.Utils.formatCurrencyString(InventoryList.getInvestingInventory(player).getAmountCache().get(currency), currency) + "&f " + Currency.getCurrencySymbol(currency)));
            if (currency != Currency.USD) {
                add(Utils.colorize("&fAktueller Wert: &a" + core.currency.Utils.formatCurrencyString(ExchangeCore.getPrice(Currency.getCurrencyPair(currency)) * InventoryList.getInvestingInventory(player).getAmountCache().get(currency), core.currency.Currency.BTC) + "&f $"));
            }
        }};

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private void addBackButton(Inventory inventory, int slot) {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.colorize("&4Zurück"));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private double calculateTotal() {
        double total = 0;
        float multiplier = 1000;
        for (int i = 0; i < values.size(); i++) {
            total += multiplier * values.get(i);
            multiplier /= 10;
        }
        return total;
    }

    private void setHashMap(double value) {
        float multiplier = 1000;
        for (int i = 0; i < values.size(); i++) {
            values.put(i, (int) (value / multiplier));
            value -= values.get(i) * multiplier;
            multiplier /= 10;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == inventory) {
            InventoryList.getInvestingInventory(player).updateCache();
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                e.getWhoClicked().openInventory(InventoryList.getInvestingInventory(player).getInventory());
            }
            if (e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                if (calculateTotal() != 0) {
                    CoreMain.mySQLMoney.convertCurrencies(player, Currency.USD, calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)), currency, calculateTotal());
                }
            }
            if (e.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                if (calculateTotal() != 0) {
                    CoreMain.mySQLMoney.convertCurrencies(player, currency, calculateTotal(), Currency.USD, calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)));
                }
            }
            if (e.getCurrentItem().getType().equals(Material.LIME_WOOL)) {
                setHashMap(getMaxBuy(currency));
                CoreMain.mySQLMoney.convertCurrencies(player, Currency.USD, calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)), currency, calculateTotal());
                setHashMap(0);
            }
            if (e.getCurrentItem().getType().equals(Material.RED_WOOL)) {
                setHashMap(getMaxSell(currency));
                CoreMain.mySQLMoney.convertCurrencies(player, currency, calculateTotal(), Currency.USD, calculateTotal() * ExchangeCore.getPrice(Currency.getCurrencyPair(currency)));
                CoreMain.mySQLMoney.setCurrency(player.getUniqueId(), currency, 0);
                setHashMap(0);
            }
            if (e.getSlot() < 18 && e.getSlot() > 8) {
                if (e.getClick().isLeftClick()) {
                    if (values.get(core.currency.Utils.getOffset(e.getSlot())) < 9) {
                        values.put(core.currency.Utils.getOffset(e.getSlot()), values.get(core.currency.Utils.getOffset(e.getSlot())) + 1);
                    }
                }
                if (e.getClick().isRightClick()) {
                    if (values.get(core.currency.Utils.getOffset(e.getSlot())) > 0) {
                        values.put(core.currency.Utils.getOffset(e.getSlot()), values.get(core.currency.Utils.getOffset(e.getSlot())) - 1);
                    }
                }
            }
            calculateTotal();
            update();
            e.setCancelled(true);
        }
    }

    private float getMaxBuy(Currency currency) {
        return InventoryList.getInvestingInventory(player).getAmountCache().get(Currency.USD) / ExchangeCore.getPrice(Currency.getCurrencyPair(currency));
    }

    private float getMaxSell(Currency currency) {
        return InventoryList.getInvestingInventory(player).getAmountCache().get(currency);
    }

    private void updater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (!inventory.getViewers().isEmpty()) {
                    update();
                }
            }
        }, 0, 1);
    }
*/
}
