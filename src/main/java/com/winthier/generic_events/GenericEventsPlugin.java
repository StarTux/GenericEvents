package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class GenericEventsPlugin extends JavaPlugin {
    @Getter private static GenericEventsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player)sender : null;
        String cmd = args.length > 0 ? args[0].toLowerCase() : null;
        if (cmd == null) {
            return false;
        } else if (cmd.equals("itemname")) {
            if (player == null) return false;
            sender.sendMessage("Item Name: '" + getItemName(player.getInventory().getItemInMainHand()) + "'");
        } else {
            return false;
        }
        return true;
    }

    public boolean playerCanBuild(Player player, Block block) {
        if (player.isOp()) return true;
        PlayerCanBuildEvent event = new PlayerCanBuildEvent(player, block);
        getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public boolean playerCanGrief(Player player, Block block) {
        if (player.isOp()) return true;
        PlayerCanGriefEvent event = new PlayerCanGriefEvent(player, block);
        getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public boolean playerCanDamageEntity(Player player, Entity entity) {
        if (player.isOp()) return true;
        PlayerCanDamageEntityEvent event = new PlayerCanDamageEntityEvent(player, entity);
        getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    // Item

    public String getItemName(ItemStack item) {
        ItemNameEvent event = new ItemNameEvent(item);
        getServer().getPluginManager().callEvent(event);
        return event.getItemName();
    }

    // Money

    public double getPlayerBalance(UUID uuid) {
        PlayerBalanceEvent event = new PlayerBalanceEvent(uuid);
        getServer().getPluginManager().callEvent(event);
        return event.getBalance();
    }

    public boolean givePlayerMoney(UUID uuid, double balance, Plugin issuingPlugin, String comment) {
        if (Double.isNaN(balance)) throw new IllegalArgumentException("Balance cannot be NaN");
        if (Double.isInfinite(balance)) throw new IllegalArgumentException("Balance cannot be infinite");
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        GivePlayerMoneyEvent event = new GivePlayerMoneyEvent(uuid, balance, issuingPlugin, comment);
        getServer().getPluginManager().callEvent(event);
        return event.isSuccessful();
    }

    public boolean takePlayerMoney(UUID uuid, double balance, Plugin issuingPlugin, String comment) {
        if (Double.isNaN(balance)) throw new IllegalArgumentException("Balance cannot be NaN");
        if (Double.isInfinite(balance)) throw new IllegalArgumentException("Balance cannot be infinite");
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        TakePlayerMoneyEvent event = new TakePlayerMoneyEvent(uuid, balance, issuingPlugin, comment);
        getServer().getPluginManager().callEvent(event);
        return event.isSuccessful();
    }

    public String formatMoney(double money) {
        FormatMoneyEvent event = new FormatMoneyEvent(money);
        getServer().getPluginManager().callEvent(event);
        if (event.getFormat() != null) return event.getFormat();
        return "" + money;
    }

    // Permission

    public boolean playerHasPermission(UUID playerId, String permission) {
        PlayerHasPermissionEvent event = new PlayerHasPermissionEvent(playerId, permission);
        getServer().getPluginManager().callEvent(event);
        return event.isPermitted();
    }
}
