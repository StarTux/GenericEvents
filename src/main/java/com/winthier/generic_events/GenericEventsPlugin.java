package com.winthier.generic_events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

    public String getItemName(ItemStack item) {
        ItemNameEvent event = new ItemNameEvent(item);
        getServer().getPluginManager().callEvent(event);
        return event.getItemName();
    }
}
