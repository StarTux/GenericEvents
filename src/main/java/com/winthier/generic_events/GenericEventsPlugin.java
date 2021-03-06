package com.winthier.generic_events;

import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class GenericEventsPlugin extends JavaPlugin implements Listener {
    @Getter private static GenericEventsPlugin instance;
    private VaultFrontend vaultFrontend = null;

    @Override
    public void onLoad() {
        if (getConfig().getBoolean("VaultFrontend") && vaultFrontend == null) {
            try {
                Class.forName("net.milkbowl.vault.economy.Economy");
                vaultFrontend = new VaultFrontend(this);
                vaultFrontend.register();
                getLogger().info("onLoad: Vault frontend enabled");
            } catch (ClassNotFoundException cnfe) { }
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        reloadConfig();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        if (getConfig().getBoolean("VaultFrontend")
            && vaultFrontend == null
            && Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            vaultFrontend = new VaultFrontend(this);
            vaultFrontend.register();
            getLogger().info("onEnable: Vault frontend enabled");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player
            ? (Player) sender
            : null;
        String cmd = args.length > 0 ? args[0].toLowerCase() : null;
        if (cmd == null) return false;
        switch (cmd) {
        case "itemname":
            if (player == null) return false;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null) {
                sender.sendMessage("No item in hand");
                return true;
            }
            sender.sendMessage("Item Name: '" + getItemName(item) + "'");
            return true;
        case "permission":
            if (args.length == 3) {
                UUID uuid = GenericEvents.cachedPlayerUuid(args[1]);
                if (uuid == null) {
                    sender.sendMessage("Unknown player: " + args[1]);
                    return true;
                }
                boolean has = playerHasPermission(uuid, args[2]);
                sender.sendMessage(args[1] + " (" + uuid + ") has " + args[2] + ": " + has);
                return true;
            }
            break;
        default:
            break;
        }
        return true;
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        switch (event.getPlugin().getName()) {
        case "Vault": {
            if (!getConfig().getBoolean("VaultFrontend")) return;
            if (vaultFrontend != null) return;
            vaultFrontend = new VaultFrontend(this);
            getLogger().info("onPluginEnable: Vault frontend enabled");
            vaultFrontend.register();
            break;
        }
        default: break;
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("Vault") && vaultFrontend != null) {
            vaultFrontend.unregister();
            vaultFrontend = null;
        }
    }

    @Deprecated
    public boolean playerCanBuild(Player player, Block block) {
        return GenericEvents.playerCanBuild(player, block);
    }

    @Deprecated
    public boolean playerCanGrief(Player player, Block block) {
        return GenericEvents.playerCanBuild(player, block);
    }

    @Deprecated
    public boolean playerCanDamageEntity(Player player, Entity entity) {
        return GenericEvents.playerCanDamageEntity(player, entity);
    }

    @Deprecated
    public String getItemName(ItemStack item) {
        return GenericEvents.getItemName(item);
    }

    @Deprecated
    public double getPlayerBalance(UUID uuid) {
        return GenericEvents.getPlayerBalance(uuid);
    }

    @Deprecated
    public boolean givePlayerMoney(UUID uuid, double balance,
                                               Plugin issuingPlugin, String comment) {
        return GenericEvents.givePlayerMoney(uuid, balance, issuingPlugin, comment);
    }

    @Deprecated
    public boolean takePlayerMoney(UUID uuid, double balance,
                                   Plugin issuingPlugin, String comment) {
        return GenericEvents.takePlayerMoney(uuid, balance, issuingPlugin, comment);
    }

    @Deprecated
    public String formatMoney(double money) {
        return GenericEvents.formatMoney(money);
    }

    @Deprecated
    public boolean playerHasPermission(UUID playerId, String permission) {
        return GenericEvents.playerHasPermission(playerId, permission);
    }
}
