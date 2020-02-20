package com.winthier.generic_events;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class GenericEvents {
    private GenericEvents() { }

    public static boolean playerCanBuild(Player player, Block block) {
        if (player.isOp()) return true;
        PlayerCanBuildEvent event = new PlayerCanBuildEvent(player, block);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean playerCanDamageEntity(Player player, Entity entity) {
        if (player.isOp()) return true;
        PlayerCanDamageEntityEvent event = new PlayerCanDamageEntityEvent(player, entity);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    // Item

    public static String getItemName(ItemStack item) {
        ItemNameEvent event = new ItemNameEvent(item);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.getItemName();
    }

    // Money

    public static double getPlayerBalance(UUID uuid) {
        PlayerBalanceEvent event = new PlayerBalanceEvent(uuid);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.getBalance();
    }

    public static boolean givePlayerMoney(UUID uuid,
                                          double balance,
                                          Plugin issuingPlugin,
                                          String comment) {
        if (Double.isNaN(balance)) {
            throw new IllegalArgumentException("Balance cannot be NaN");
        }
        if (Double.isInfinite(balance)) {
            throw new IllegalArgumentException("Balance cannot be infinite");
        }
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        GivePlayerMoneyEvent event = new GivePlayerMoneyEvent(uuid, balance,
                                                              issuingPlugin, comment);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.isSuccessful();
    }

    public static boolean takePlayerMoney(UUID uuid,
                                          double balance,
                                          Plugin issuingPlugin,
                                          String comment) {
        if (Double.isNaN(balance)) {
            throw new IllegalArgumentException("Balance cannot be NaN");
        }
        if (Double.isInfinite(balance)) {
            throw new IllegalArgumentException("Balance cannot be infinite");
        }
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        TakePlayerMoneyEvent event = new TakePlayerMoneyEvent(uuid, balance,
                                                              issuingPlugin, comment);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.isSuccessful();
    }

    public static String formatMoney(double money) {
        FormatMoneyEvent event = new FormatMoneyEvent(money);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.getFormat() != null) return event.getFormat();
        return "" + money;
    }

    // Permission

    public static boolean playerHasPermission(UUID playerId, String permission) {
        if (playerId == null) throw new NullPointerException("Player UUID cannot be null");
        if (permission == null) throw new NullPointerException("Permission cannot be null");
        PlayerHasPermissionEvent event = new PlayerHasPermissionEvent(playerId, permission);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.isPermitted();
    }

    // Cache

    public static String cachedPlayerName(UUID playerId) {
        if (playerId == null) throw new NullPointerException("Player UUID cannot be null");
        PlayerCacheEvent event = new PlayerCacheEvent(playerId);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.getName();
    }

    public static UUID cachedPlayerUuid(String playerName) {
        if (playerName == null) throw new NullPointerException("Player name cannot be null");
        PlayerCacheEvent event = new PlayerCacheEvent(playerName);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.getUniqueId();
    }
}
