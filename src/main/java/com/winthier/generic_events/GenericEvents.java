package com.winthier.generic_events;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import java.util.UUID;

public final class GenericEvents {
    private GenericEvents() { }

    public static boolean playerCanBuild(Player player, Block block) {
        if (player.isOp()) return true;
        PlayerCanBuildEvent event = new PlayerCanBuildEvent(player, block);
        Server.getInstance().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean playerCanDamageEntity(Player player, Entity entity) {
        if (player.isOp()) return true;
        PlayerCanDamageEntityEvent event = new PlayerCanDamageEntityEvent(player, entity);
        Server.getInstance().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    // Item

    public static String getItemName(Item item) {
        ItemNameEvent event = new ItemNameEvent(item);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.getItemName();
    }

    // Money

    public static double getPlayerBalance(UUID uuid) {
        PlayerBalanceEvent event = new PlayerBalanceEvent(uuid);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.getBalance();
    }

    public static boolean givePlayerMoney(UUID uuid, double balance, Plugin issuingPlugin, String comment) {
        if (Double.isNaN(balance)) throw new IllegalArgumentException("Balance cannot be NaN");
        if (Double.isInfinite(balance)) throw new IllegalArgumentException("Balance cannot be infinite");
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        GivePlayerMoneyEvent event = new GivePlayerMoneyEvent(uuid, balance, issuingPlugin, comment);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.isSuccessful();
    }

    public static boolean takePlayerMoney(UUID uuid, double balance, Plugin issuingPlugin, String comment) {
        if (Double.isNaN(balance)) throw new IllegalArgumentException("Balance cannot be NaN");
        if (Double.isInfinite(balance)) throw new IllegalArgumentException("Balance cannot be infinite");
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        TakePlayerMoneyEvent event = new TakePlayerMoneyEvent(uuid, balance, issuingPlugin, comment);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.isSuccessful();
    }

    public static String formatMoney(double money) {
        FormatMoneyEvent event = new FormatMoneyEvent(money);
        Server.getInstance().getPluginManager().callEvent(event);
        if (event.getFormat() != null) return event.getFormat();
        return "" + money;
    }

    // Permission

    public static boolean playerHasPermission(UUID playerId, String permission) {
        if (playerId == null) throw new NullPointerException("Player UUID cannot be null");
        if (permission == null) throw new NullPointerException("Permission cannot be null");
        PlayerHasPermissionEvent event = new PlayerHasPermissionEvent(playerId, permission);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.isPermitted();
    }

    // Cache

    public static String cachedPlayerName(UUID playerId) {
        if (playerId == null) throw new NullPointerException("Player UUID cannot be null");
        PlayerCacheEvent event = new PlayerCacheEvent(playerId);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.getName();
    }

    public static UUID cachedPlayerUuid(String playerName) {
        if (playerName == null) throw new NullPointerException("Player name cannot be null");
        PlayerCacheEvent event = new PlayerCacheEvent(playerName);
        Server.getInstance().getPluginManager().callEvent(event);
        return event.getUniqueId();
    }
}
